package Forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * InstructorAttendanceTable — builds the table model that AttendanceLogInst uses.
 *
 * NOTE: uses AttendID as the attendance PK column name.
 */
public class InstructorAttendanceTable {

    private JTable table;
    private DefaultTableModel tableModel;
    private Connection conn;

    // Constructor
    public InstructorAttendanceTable(Connection conn, JTable existingTable) {
        this.conn = conn;
        this.table = existingTable;

        // columns: last two are hidden internal IDs
        String[] columns = {
            "Course Name", "Subject",
            "Last Name", "First Name", "Middle Name",
            "Year", "Section",
            "Schedule", "Day", "Time In", "Status",
            "AttendID", "StudID"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);

        // styling (unchanged)
        table.setFont(new java.awt.Font("Poppins", java.awt.Font.PLAIN, 12));
        table.setBackground(java.awt.Color.WHITE);
        table.setForeground(java.awt.Color.BLACK);
        table.setGridColor(new java.awt.Color(230, 230, 230));
        table.setRowHeight(28);
        table.setShowGrid(true);
        table.setIntercellSpacing(new java.awt.Dimension(0, 1));
        table.setSelectionBackground(new java.awt.Color(240, 240, 240));
        table.setSelectionForeground(java.awt.Color.BLACK);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setFocusable(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new java.awt.Font("Poppins", java.awt.Font.PLAIN, 12));
        header.setBackground(java.awt.Color.WHITE);
        header.setForeground(java.awt.Color.BLACK);
        header.setOpaque(true);
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(220, 220, 220)));

        if (table.getParent() instanceof JScrollPane scroll) {
            scroll.setBorder(BorderFactory.createEmptyBorder());
            scroll.getViewport().setBackground(java.awt.Color.WHITE);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

            scroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
                @Override
                protected void configureScrollBarColors() {
                    this.thumbColor = new java.awt.Color(200, 200, 200);
                    this.trackColor = java.awt.Color.WHITE;
                }
            });
        }

        hideInternalColumnsIfPresent();
    }

    // hideInternalColumnsIfPresent()
    private void hideInternalColumnsIfPresent() {
        try {
            int colCount = table.getColumnModel().getColumnCount();
            int attendColIndex = colCount - 2;
            int studColIndex = colCount - 1;

            if (attendColIndex >= 0) {
                var col = table.getColumnModel().getColumn(attendColIndex);
                col.setMinWidth(0); col.setMaxWidth(0); col.setPreferredWidth(0); col.setResizable(false);
            }
            if (studColIndex >= 0) {
                var col = table.getColumnModel().getColumn(studColIndex);
                col.setMinWidth(0); col.setMaxWidth(0); col.setPreferredWidth(0); col.setResizable(false);
            }
        } catch (Exception ex) {
            // ignore if column model not ready
        }
    }

    // loadData(...)
    public void loadData(String instID, Integer courseID, Integer subID, Integer year, String section) {
        String sql =
            "SELECT a.AttendID, a.StudID, c.CourseName, sub.SubName, st.Lname, st.Fname, st.Mname, " +
            "       st.Year, st.Section, sch.SchedID, a.DayOfWeek, a.TimeStamp, a.Status " +
            "FROM tblattendance a " +
            "JOIN tblstudent st ON a.StudID = st.StudID " +
            "JOIN tblschedule sch ON a.SchedID = sch.SchedID " +
            "JOIN tblsubject sub ON sch.SubID = sub.SubID " +
            "JOIN tblcourse c ON sub.CourseID = c.CourseID " +
            "WHERE sch.InstID = ? " +
            "AND (? IS NULL OR c.CourseID = ?) " +
            "AND (? IS NULL OR sub.SubID = ?) " +
            "AND (? IS NULL OR st.Year = ?) " +
            "AND (? IS NULL OR st.Section = ?) " +
            "ORDER BY c.CourseName, sub.SubName, st.Lname, st.Fname";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setObject(1, instID);
            pst.setObject(2, courseID); pst.setObject(3, courseID);
            pst.setObject(4, subID); pst.setObject(5, subID);
            pst.setObject(6, year); pst.setObject(7, year);
            pst.setObject(8, section); pst.setObject(9, section);

            try (ResultSet rs = pst.executeQuery()) {
                tableModel.setRowCount(0);

                while (rs.next()) {
                    Object[] row = {
                        rs.getString("CourseName"),
                        rs.getString("SubName"),
                        rs.getString("Lname"),
                        rs.getString("Fname"),
                        rs.getString("Mname"),
                        rs.getString("Year"),
                        rs.getString("Section"),
                        rs.getString("SchedID"),
                        rs.getString("DayOfWeek"),
                        rs.getTime("TimeStamp"),
                        rs.getString("Status"),
                        rs.getString("AttendID"), // hidden PK
                        rs.getString("StudID")    // hidden StudID
                    };
                    tableModel.addRow(row);
                }
            }

            hideInternalColumnsIfPresent();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading attendance data: " + e.getMessage());
        }
    }
}