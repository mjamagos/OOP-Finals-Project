/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Central DB and native library configuration.
 * - Update constants here if you change DB credentials or OpenCV DLL path.
 * - Use DBConfig.getConnection() throughout the app.
 */
public final class DBConfig {
    private DBConfig() {}

    public static final String URL = "jdbc:mysql://localhost:3306/classattendance";
    public static final String USER = "root";
    public static final String PASS = "";

    private static String OPEN_CV_DLL_PATH = "C:/opencv/build/java/x64/opencv_java4120.dll";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static String getOpenCvDllPath() {
        return OPEN_CV_DLL_PATH;
    }

    public static void setOpenCvDllPath(String path) {
        if (path != null && !path.isEmpty()) OPEN_CV_DLL_PATH = path;
    }
}