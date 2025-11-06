/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

/**
 *
 * @author Jobelle
 */
import java.sql.*;
public class TestDB {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/classattendance", "root", "");
            PreparedStatement pst = conn.prepareStatement(
                    "SELECT s.StudID, s.Lname, s.Fname, s.Mname, "
                    + "c.CourseName, sub.SubName, s.Year, s.Section " +
                    "FROM tblstudent s "+
                    "JOIN tblschedule sch ON s.CourseID = sch.CourseID AND s.Year = sch.Year AND s.Section = sch.Section " +
                    "JOIN tblsubject sub ON sch.SubID = sub.SubID " +
                    "JOIN tblcourse c ON s.CourseID = c.CourseID " +
                    "WHERE sch.InstID = ? ");  // Your full query
                    
            pst.setString(1, "CCIT-003");
            ResultSet rs = pst.executeQuery();
            int count = 0;
            while (rs.next()) { count++; System.out.println("Row: " + rs.getString(1)); }
            System.out.println("Total rows: " + count);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
