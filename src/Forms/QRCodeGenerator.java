package Forms;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * QRCodeGenerator — centralized QR helpers (create, save, batch generate).
 * This is the renamed replacement for the old generateQR helper.
 */
public final class QRCodeGenerator {
    private QRCodeGenerator() {}

    public static BufferedImage createQRCodeImage(String text, int width, int height) throws WriterException {
        if (text == null) text = "";
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public static void saveQRCodeImage(BufferedImage img, File outFile) throws IOException {
        if (img == null) throw new IllegalArgumentException("img is null");
        if (outFile == null) throw new IllegalArgumentException("outFile is null");
        File parent = outFile.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();
        ImageIO.write(img, "PNG", outFile);
    }

    /**
     * Generate QR PNG files for all students in tblstudent and save them to folderPath.
     * Uses DBConfig.getConnection() for credentials.
     */
    public static BatchResult generateQRCodesForAllStudents(String folderPath, int width, int height) {
        BatchResult result = new BatchResult();
        if (folderPath == null || folderPath.trim().isEmpty()) {
            result.error = "No output folder provided";
            return result;
        }
        File outDir = new File(folderPath);
        if (!outDir.exists() && !outDir.mkdirs()) {
            result.error = "Failed to create output folder: " + folderPath;
            return result;
        }

        String sql = "SELECT StudID FROM tblstudent";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String studID = rs.getString("StudID");
                if (studID == null || studID.trim().isEmpty()) {
                    result.skipped++;
                    continue;
                }
                studID = studID.trim();
                try {
                    BufferedImage qr = createQRCodeImage(studID, width, height);
                    File out = new File(outDir, studID + ".png");
                    saveQRCodeImage(qr, out);
                    result.success++;
                } catch (WriterException | IOException we) {
                    result.failed++;
                    result.failedIds.add(studID);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            result.error = "DB error: " + e.getMessage();
        }
        return result;
    }

    public static final class BatchResult {
        public int success = 0;
        public int failed = 0;
        public int skipped = 0;
        public List<String> failedIds = new ArrayList<>();
        public String error = null;

        public String summary() {
            if (error != null && !error.isEmpty()) return "Error: " + error;
            return String.format("Done: %d success, %d failed, %d skipped", success, failed, skipped);
        }
    }
}