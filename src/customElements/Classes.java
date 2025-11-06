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
        setBackground(java.awt.Color.WHITE);

        // Initialize table
        model = new DefaultTableModel();
        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        model.setColumnIdentifiers(new Object[]{
            "Student ID", "Last Name", "First Name", "Middle Name",
            "Course", "Subject", "Year", "Section"
        });

        // ====== TABLE STYLE ======
        table.setFont(new java.awt.Font("Poppins", java.awt.Font.PLAIN, 12));
        table.setBackground(java.awt.Color.WHITE);
        table.setForeground(java.awt.Color.BLACK);
        table.setGridColor(new java.awt.Color(230, 230, 230)); // light grid lines
        table.setRowHeight(28);
        table.setShowGrid(true);
        table.setIntercellSpacing(new java.awt.Dimension(0, 1));
        table.setSelectionBackground(new java.awt.Color(240, 240, 240)); // subtle gray when selected
        table.setSelectionForeground(java.awt.Color.BLACK);
        table.setBorder(BorderFactory.createEmptyBorder());
        table.setFocusable(false);

        // Remove ugly borders
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);

        // ====== HEADER STYLE ======
        JTableHeader header = table.getTableHeader();
        header.setFont(new java.awt.Font("Poppins", java.awt.Font.PLAIN, 12));
        header.setBackground(java.awt.Color.WHITE);
        header.setForeground(java.awt.Color.BLACK);
        header.setReorderingAllowed(false);
        header.setOpaque(true);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(220, 220, 220)));

        // ====== SCROLLPANE ======
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(java.awt.Color.WHITE);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Make scrollbar minimal (flat)
        scroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new java.awt.Color(200, 200, 200);
                this.trackColor = java.awt.Color.WHITE;
            }
        });

        add(scroll, java.awt.BorderLayout.CENTER);
    }


    /**
     * Load attendance data into the table based on instructor ID and filters
     * @param instID Logged-in instructor ID
     * @param courseName Selected course filter (null = all)
     * @param subName Selected subject filter (null = all)
     * @param year Selected student year filter (null = all)
     * @param section Selected student section filter (null = all)
     * @param conn DB connection
     */
    public void loadData(String instID, String courseName, String subName, String year, String section, Connection conn) {
        this.conn = conn;
        model.setRowCount(0); // clear table

        System.out.println("DEBUG → Instructor ID: " + instID);

        try {
            StringBuilder sql = new StringBuilder();
            sql.append(
                "SELECT DISTINCT " +
                "   s.StudID, s.Lname, s.Fname, s.Mname, " +
                "   c.CourseName, sub.SubName, s.Year, s.Section " +
                "FROM tblstudent s " +
                "JOIN tblcourse c ON s.CourseID = c.CourseID " +
                "JOIN tblschedule sch ON " +
                "       s.CourseID = sch.CourseID " +
                "   AND s.Year = sch.Year " +
                "   AND s.Section = sch.Section " +
                "JOIN tblsubject sub ON sch.SubID = sub.SubID " +
                "WHERE sch.InstID = ? "
            );


            List<Object> params = new ArrayList<>();
            params.add(instID);

            if (courseName != null && !courseName.equals("All")) {
                sql.append("AND c.CourseName = ? ");
                params.add(courseName);
            }
            if (subName != null && !subName.equals("All")) {
                sql.append("AND sub.SubName = ? ");
                params.add(subName);
            }
            if (year != null && !year.equals("All")) {
                sql.append("AND s.Year = ? ");
                params.add(year);
            }
            if (section != null && !section.equals("All")) {
                sql.append("AND s.Section = ? ");
                params.add(section);
            }

            PreparedStatement pst = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                pst.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pst.executeQuery();

            boolean hasRows = false;
            while (rs.next()) {
                hasRows = true;
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

            System.out.println("DEBUG → Query ran successfully, any rows? " + hasRows);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    // Attach combo boxes for filtering and reload table from DB on change
    public void attachFilters(JComboBox<String> comboCourse,
                              JComboBox<String> comboSubject,
                              JComboBox<String> comboYear,
                              JComboBox<String> comboSection,
                              String instID,
                              Connection conn) {

        Runnable reloadTable = () -> {
            String selectedCourse = comboCourse.getSelectedItem() == null ? null : comboCourse.getSelectedItem().toString();
            String selectedSubject = comboSubject.getSelectedItem() == null ? null : comboSubject.getSelectedItem().toString();
            String selectedYear = comboYear.getSelectedItem() == null ? null : comboYear.getSelectedItem().toString();
            String selectedSection = comboSection.getSelectedItem() == null ? null : comboSection.getSelectedItem().toString();

            // Treat both "All" and your GUI placeholders (e.g. "Course", "Year", "Subject", "Section")
            // as "no filter" so they become null and are not added to the WHERE clause.
            if (selectedCourse != null && (selectedCourse.equals("All") || selectedCourse.equals("Course") || selectedCourse.trim().isEmpty()))
                selectedCourse = null;

            if (selectedSubject != null && (selectedSubject.equals("All") || selectedSubject.equals("Subject") || selectedSubject.trim().isEmpty()))
                selectedSubject = null;

            if (selectedYear != null && (selectedYear.equals("All") || selectedYear.equals("Year") || selectedYear.trim().isEmpty()))
                selectedYear = null;

            if (selectedSection != null && (selectedSection.equals("All") || selectedSection.equals("Section") || selectedSection.trim().isEmpty()))
                selectedSection = null;

            loadData(instID, selectedCourse, selectedSubject, selectedYear, selectedSection, conn);
        };

        comboCourse.addActionListener(e -> reloadTable.run());
        comboSubject.addActionListener(e -> reloadTable.run());
        comboYear.addActionListener(e -> reloadTable.run());
        comboSection.addActionListener(e -> reloadTable.run());
    }

    public JTable getTable() {
        return table;
    }
}