/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Forms;

import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Jobelle
 */

/**
 * Functional mapper: given a DefaultTableModel and a model row index,
 * return a StudentRow describing the values for that row.
 */
public interface RowToStudentMapper {
    StudentRow map(DefaultTableModel model, int modelRow);
}