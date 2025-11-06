/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Forms;

/**
 *
 * @author Jobelle
 */
public class StudentRow {
    public final String studID;   // may be null/empty if not present in table
    public final String fname;
    public final String mname;
    public final String lname;
    public final String subject;
    public final String course;
    public final String year;
    public final String section;

    public StudentRow(String studID, String fname, String mname, String lname,
                      String subject, String course, String year, String section) {
        this.studID = studID;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.subject = subject;
        this.course = course;
        this.year = year;
        this.section = section;
    }
}

