package Forms;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * PDFExportUtil
 *
 * Small orchestrator: Show SavePDFDialog + FileDialog, then delegate to PDFAttendance.export(...)
 * It also attempts to load header/footer images from classpath:
 *   /pdf_templates/header.png  (used as page header)
 *   /pdf_templates/footer.png  (used as page footer background)
 *
 * To use your header/footer images:
 * - Put header.png and footer.png into your classpath (e.g. src/main/resources/pdf_templates/ or
 *   NetBeans project's resources folder). The exporter will load them automatically if present.
 */
public final class PDFExportUtil {
    private PDFExportUtil() {}

    public static void exportTable(Frame owner, JTable table) {
        exportTable(owner, table, null, null);
    }

    /**
     * Export, attempting to auto-load template header/footer images from the classpath if present.
     * You can pass explicit header/footer BufferedImage via the last two parameters (both nullable).
     */
    public static void exportTable(Frame owner, JTable table, BufferedImage headerImage, BufferedImage footerImage) {
        if (table == null) {
            JOptionPane.showMessageDialog(owner,
                    "Table is not available for export.",
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1) Ask for page settings
        SavePDFDialog dlg = new SavePDFDialog(owner, true);
        dlg.setLocationRelativeTo(owner);
        dlg.setVisible(true);
        if (!dlg.isConfirmed()) return;

        // 2) Ask for output file
        FileDialog fd = new FileDialog(owner, "Save PDF", FileDialog.SAVE);
        fd.setFile("Attendance.pdf");
        fd.setVisible(true);
        if (fd.getFile() == null) return;

        String filename = fd.getFile();
        if (!filename.toLowerCase().endsWith(".pdf")) filename += ".pdf";
        File outputFile = new File(fd.getDirectory(), filename);

        // 3) Settings
        String size = dlg.getSelectedSize();
        boolean landscape = "Landscape".equalsIgnoreCase(dlg.getSelectedOrientation());
        float[] margins = PDFAttendance.convertMargins(dlg.getSelectedMargin());

        // 4) If header/footer images were not supplied by caller, attempt to load them from classpath
        if (headerImage == null) {
            headerImage = loadResourceImage("/images/Header.png");
        }
        if (footerImage == null) {
            footerImage = loadResourceImage("/images/Footer.png");
        }

        // 5) Delegate to PDFAttendance
        try {
            PDFAttendance.export(
                    table,
                    outputFile,
                    size,
                    landscape,
                    margins[0], margins[1], margins[2], margins[3],
                    headerImage,
                    footerImage
            );
            JOptionPane.showMessageDialog(owner,
                    "PDF saved successfully: " + outputFile.getAbsolutePath(),
                    "Export Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(owner,
                    "Error saving PDF: " + ex.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static BufferedImage loadResourceImage(String resourcePath) {
        try (InputStream in = PDFExportUtil.class.getResourceAsStream(resourcePath)) {
            if (in == null) return null;
            return ImageIO.read(in);
        } catch (Exception e) {
            // no template present or failed to read -> ignore and continue without images
            e.printStackTrace();
            return null;
        }
    }
}