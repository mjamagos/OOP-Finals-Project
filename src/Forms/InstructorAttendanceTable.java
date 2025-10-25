/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

/**
 *
 * @author Jobelle
 */
/*
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class InstructorAttendanceTable {

    private JTable table;
    private DefaultTableModel tableModel;
    private Connection conn;

    /**
     * Constructor
     * @param conn Database connection
     * @param existingTable JTable from your GUI builder
     */
    public InstructorAttendanceTable(Connection conn, JTable existingTable) {
        this.conn = conn;
        this.table = existingTable;

        // Column names match your attendance table example
        String[] columns = {"CourseName", "Subject", "Last Name", "First Name", "Middle Name",
                            "Schedule", "Day", "Time in", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            // Make table cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(tableModel);
    }

    /**
     * Load attendance data into the table based on instructor ID and filters
     * @param instID Logged-in instructor ID
     * @param courseID Selected course filter (null = all)
     * @param subID Selected subject filter (null = all)
     * @param year Selected student year filter (null = all)
     * @param section Selected student section filter (null = all)
     */
    public void loadData(String instID, Integer courseID, Integer subID, Integer year, String section) {
        try {
            String sql = "SELECT c.CourseName, s.SubName, st.Lname, st.Fname, st.Mname, " +
                         "sch.SchedID, a.DayOfWeek, a.TimeStamp, a.Status " +
                         "FROM tblattendance a " +
                         "JOIN tblstudent st ON a.StudID = st.StudID " +
                         "JOIN tblschedule sch ON a.SchedID = sch.SchedID " +
                         "JOIN tblsubject s ON s.SubID = sch.SubID " +
                         "JOIN tblcourse c ON s.CourseID = c.CourseID " +
                         "WHERE s.InstID = ? " +
                         "AND (? IS NULL OR c.CourseID = ?) " +
                         "AND (? IS NULL OR s.SubID = ?) " +
                         "AND (? IS NULL OR st.Year = ?) " +
                         "AND (? IS NULL OR st.Section = ?) " +
                         "ORDER BY c.CourseName, s.SubName, st.Lname, st.Fname";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setObject(1, instID);
            pst.setObject(2, courseID); pst.setObject(3, courseID);
            pst.setObject(4, subID); pst.setObject(5, subID);
            pst.setObject(6, year); pst.setObject(7, year);
            pst.setObject(8, section); pst.setObject(9, section);

            ResultSet rs = pst.executeQuery();
            tableModel.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                Object[] row = {
                        rs.getString("CourseName"),
                        rs.getString("SubName"),
                        rs.getString("Lname"),
                        rs.getString("Fname"),
                        rs.getString("Mname"),
                        rs.getString("SchedID"),
                        rs.getString("DayOfWeek"),
                        rs.getTime("TimeStamp"),
                        rs.getString("Status")
                };
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading attendance data: " + e.getMessage());
        }
    }
}
