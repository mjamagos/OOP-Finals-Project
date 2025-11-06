package Forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Reusable static helper to open the same edit dialog for different tables/frames.
 * Uses StudentRepo for all DB interactions (type changed from StudentDAO).
 */
public final class StudentEditLauncher {
    private StudentEditLauncher() {}

    /**
     * Opens dialogEditInfo for the currently selected row in the provided table.
     *
     * @param owner  Frame owner for the dialog (use (Frame) SwingUtilities.getWindowAncestor(table))
     * @param table  JTable containing the selection (may be sorted/filtered)
     * @param repo   StudentRepo to use for lookups/updates (was StudentDAO)
     * @param mapper maps (model, modelRow) -> StudentRow for this table's column layout
     * @param onSaved optional callback executed after a successful save (can be null)
     */
    public static void openEditDialogFromTable(Frame owner,
                                               JTable table,
                                               StudentRepo repo,
                                               RowToStudentMapper mapper,
                                               Runnable onSaved) {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) return;

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int modelRow = table.convertRowIndexToModel(viewRow);

        StudentRow row = mapper.map(model, modelRow);

        // 1) Try to use StudID from table first
        String studID = (row.studID == null || row.studID.isEmpty()) ? "" : row.studID.trim();

        // 2) If not present, try to resolve via repo.findStudID (more tolerant query)
        if ((studID == null || studID.isEmpty()) && repo != null) {
            studID = repo.findStudID(row);
        }

        // 3) If still not found, show a clear message and do not open the dialog
        if (studID == null || studID.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(owner,
                "Cannot determine the student's ID (StudID).\n" +
                "Please open the edit dialog from a student row that includes the StudID or ensure the student exists in the database.",
                "Can't locate student",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4) Fetch authoritative DB values if possible
        StudentRow finalRow = row;
        if (repo != null) {
            StudentRow dbRow = repo.fetchStudentByID(studID);
            if (dbRow != null) finalRow = dbRow;
        }

        dialogEditInfo dialog = new dialogEditInfo(owner, true, repo, studID == null ? "" : studID);
        dialog.loadData(finalRow.fname, finalRow.mname, finalRow.lname,
                        finalRow.subject, finalRow.course, finalRow.year, finalRow.section);

        dialog.getButtonSave().addActionListener(e -> {
            try {
                dialog.saveChanges(); // dialog uses repo internally
                dialog.dispose();
                if (onSaved != null) onSaved.run();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(owner, "Error saving student: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.getButtonCancel().addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }
}