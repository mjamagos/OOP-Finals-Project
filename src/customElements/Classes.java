/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customElements;

/**
 *
 * @author Jobelle
 */
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.awt.event.*;

public class Classes extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private Connection conn;

    public Classes() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new java.awt.BorderLayout());

        // Initialize table
        model = new DefaultTableModel();
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        model.setColumnIdentifiers(new Object[]{
                "Student ID", "Last Name", "First Name", "Middle Name",
                "Course", "Subject", "Year", "Section"
        });

        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);
    }

    // Public method to load data for an instructor
    public void loadData(int instructorID, Connection conn) {
        this.conn = conn;
        model.setRowCount(0); // clear table
        try {
            String sql = "SELECT s.StudID, s.Lname, s.Fname, s.Mname, " +
                    "c.CourseName, sub.SubName, s.Year, s.Section " +
                    "FROM tblstudent s " +
                    "JOIN tblschedule sch ON s.CourseID = sch.CourseID AND s.Year = sch.Year AND s.Section = sch.Section " +
                    "JOIN tblsubject sub ON sch.SubID = sub.SubID " +
                    "JOIN tblcourse c ON s.CourseID = c.CourseID " +
                    "WHERE sch.InstructorID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, instructorID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("StudID"),
                        rs.getString("Lname"),
                        rs.getString("Fname"),
                        rs.getString("Mname"),
                        rs.getString("CourseName"),
                        rs.getString("SubName"),
                        rs.getString("Year"),
                        rs.getString("Section")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Public method to attach combo boxes for filtering
    public void attachFilters(JComboBox<String> comboCourse,
                              JComboBox<String> comboSubject,
                              JComboBox<String> comboYear,
                              JComboBox<String> comboSection) {

        addFilterOptions(comboCourse, 4);
        addFilterOptions(comboSubject, 5);
        addFilterOptions(comboYear, 6);
        addFilterOptions(comboSection, 7);

        ActionListener filterListener = e -> applyFilters(comboCourse, comboSubject, comboYear, comboSection);
        comboCourse.addActionListener(filterListener);
        comboSubject.addActionListener(filterListener);
        comboYear.addActionListener(filterListener);
        comboSection.addActionListener(filterListener);
    }

    private void addFilterOptions(JComboBox<String> comboBox, int column) {
        comboBox.addItem("All");
        Set<String> items = new LinkedHashSet<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            items.add(model.getValueAt(i, column).toString());
        }
        for (String item : items) comboBox.addItem(item);
    }

    private void applyFilters(JComboBox<String> comboCourse,
                              JComboBox<String> comboSubject,
                              JComboBox<String> comboYear,
                              JComboBox<String> comboSection) {

        List<RowFilter<Object,Object>> filters = new ArrayList<>();
        if (!comboCourse.getSelectedItem().equals("All"))
            filters.add(RowFilter.regexFilter(comboCourse.getSelectedItem().toString(), 4));
        if (!comboSubject.getSelectedItem().equals("All"))
            filters.add(RowFilter.regexFilter(comboSubject.getSelectedItem().toString(), 5));
        if (!comboYear.getSelectedItem().equals("All"))
            filters.add(RowFilter.regexFilter(comboYear.getSelectedItem().toString(), 6));
        if (!comboSection.getSelectedItem().equals("All"))
            filters.add(RowFilter.regexFilter(comboSection.getSelectedItem().toString(), 7));

        sorter.setRowFilter(filters.isEmpty() ? null : RowFilter.andFilter(filters));
    }

    public JTable getTable() {
        return table;
    }
}
