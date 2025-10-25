/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OpenCV;

/**
 *
 * @author Jobelle
 */
import java.sql.*;
import java.io.IOException;
import java.nio.file.Paths;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class GenerateStudentQRCodes {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/classattendance";
        String user = "root";
        String pass = "";

        String folderPath = "C:\\Users\\Jobelle\\Downloads\\FinalProject\\QRCodes\\";
        int width = 300;
        int height = 300;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // load driver first
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {

            System.out.println("Connected to database");

            String sql = "SELECT StudID FROM tblstudent";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            QRCodeWriter qrWriter = new QRCodeWriter();

            while (rs.next()) {
                String studID = rs.getString("StudID");

                String csvData = String.join(",", studID);

                try {
                    BitMatrix bitMatrix = qrWriter.encode(csvData, BarcodeFormat.QR_CODE, width, height);
                    String filePath = folderPath + studID + ".png";
                    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(filePath));
                    System.out.println("QR generated for " + studID);
                } catch (WriterException | IOException e) {
                    System.err.println("Failed to generate QR for " + studID + ": " + e.getMessage());
                }
            }

            rs.close();
            stmt.close();
            System.out.println("?Done generating all QR codes!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
