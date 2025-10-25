/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customElements; 

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomAttendanceTable extends JTable {

    private DefaultTableModel tableModel;

    public CustomAttendanceTable() {
        UIManager.put("TableHeader.background", new Color(255,255,255));
        // Define columns
        String[] columns = {"Course", "Subject", "Last Name", "First Name", "Middle Name",
                            "Schedule", "Day", "Time In", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            // Make cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        setModel(tableModel);

        // Optional: visual customizations
        setFillsViewportHeight(true);
        setRowHeight(25);
        setShowGrid(true);
        setGridColor(Color.LIGHT_GRAY);
        getTableHeader().setReorderingAllowed(true);
        getTableHeader().setBackground(Color.WHITE);
        getTableHeader().setFont(new Font("Poppins", Font.PLAIN, 12));
        setFont(new Font("Poppins", Font.PLAIN, 12));
        setBackground(Color.WHITE); 
        
        
    }

    // Allow external code to populate rows
    public void setTableData(Object[][] data) {
        tableModel.setRowCount(0); // clear existing
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    // Get access to the model for InstructorAttendanceTable
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
