package Forms;

import java.sql.Timestamp;

/**
 * Lightweight holder for a single attendance row match returned by DAO.
 */
public class AttendanceRecord {
    public final String attendId; // primary key in tblattendance (AttendID)
    public final String studID;
    public final String schedID;
    public final Timestamp timeStamp;
    public final String status;
    public final String courseName;
    public final String subjectName;
    public final String fname;
    public final String mname;
    public final String lname;
    public final String year;
    public final String section;

    public AttendanceRecord(String attendId,
                            String studID,
                            String schedID,
                            Timestamp timeStamp,
                            String status,
                            String courseName,
                            String subjectName,
                            String fname,
                            String mname,
                            String lname,
                            String year,
                            String section) {
        this.attendId = attendId;
        this.studID = studID;
        this.schedID = schedID;
        this.timeStamp = timeStamp;
        this.status = status;
        this.courseName = courseName;
        this.subjectName = subjectName;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.year = year;
        this.section = section;
    }

    @Override
    public String toString() {
        String ts = (timeStamp == null) ? "unknown time" : timeStamp.toString();
        String name = (lname == null ? "" : lname) + ", " + (fname == null ? "" : fname)
                + (mname != null && !mname.isEmpty() ? " " + mname : "");
        return String.format("%s — %s | %s | %s | %s",
                attendId == null ? "?" : attendId,
                name.trim(),
                subjectName == null ? "" : subjectName,
                courseName == null ? "" : courseName,
                ts);
    }
}