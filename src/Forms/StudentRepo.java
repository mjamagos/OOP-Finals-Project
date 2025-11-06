package Forms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentRepo — single place for student/attendance related DB operations.
 *
 * This file provides the methods that UI code expects:
 *   - findStudID(StudentRow)
 *   - fetchStudentByID(String)
 *   - updateStudent(String, StudentRow)
 *   - deleteAttendanceByStudID(String)
 *   - deleteAttendanceForStudOnDate(String, Date)
 *   - deleteAttendanceById(String)
 *   - findAttendanceMatches(...)
 *
 * Note: The attendance table primary key column is AttendID (not AttendanceID).
 */
public class StudentRepo {
    private final Connection conn;

    public StudentRepo(Connection conn) {
        this.conn = conn;
    }

    // -----------------------------
    // Student lookups / fetch
    // -----------------------------
    /**
     * Find a StudID from student-identifying fields.
     *
     * This version is tolerant:
     *  - compares lowercase trimmed names,
     *  - treats empty MName/Year/Section as "don't care" (matches any),
     *  - returns the first matching StudID (LIMIT 1).
     *
     * Prefer having the StudID available in the table model; this is a best-effort helper.
     */
    public String findStudID(StudentRow row) {
        if (conn == null || row == null) return "";
        String sql =
            "SELECT StudID FROM tblstudent " +
            "WHERE LOWER(TRIM(LName)) = LOWER(TRIM(?)) " +
            "  AND LOWER(TRIM(FName)) = LOWER(TRIM(?)) " +
            "  AND (? = '' OR LOWER(TRIM(MName)) = LOWER(TRIM(?))) " +
            "  AND (? = '' OR Year = ?) " +
            "  AND (? = '' OR Section = ?) " +
            "LIMIT 1";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, row.lname == null ? "" : row.lname);
            pst.setString(2, row.fname == null ? "" : row.fname);

            String mname = row.mname == null ? "" : row.mname;
            pst.setString(3, mname);
            pst.setString(4, mname);

            String year = row.year == null ? "" : row.year;
            pst.setString(5, year);
            pst.setString(6, year);

            String section = row.section == null ? "" : row.section;
            pst.setString(7, section);
            pst.setString(8, section);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getString("StudID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * Fetch authoritative student data from DB by StudID.
     * Returns null if not found.
     */
    public StudentRow fetchStudentByID(String studID) {
        if (conn == null || studID == null || studID.isEmpty()) return null;
        String sql = "SELECT FName, MName, LName, Year, Section, CourseID FROM tblstudent WHERE StudID = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, studID);
            try (ResultSet rs = pst.executeQuery()) {
                if (!rs.next()) return null;
                String fname = rs.getString("FName");
                String mname = rs.getString("MName");
                String lname = rs.getString("LName");
                String year  = rs.getString("Year");
                String section = rs.getString("Section");
                String course = "";
                String subject = "";

                String courseID = rs.getString("CourseID");
                if (courseID != null && !courseID.isEmpty()) {
                    // try get course name
                    try (PreparedStatement pc = conn.prepareStatement("SELECT CourseName FROM tblcourse WHERE CourseID = ?")) {
                        pc.setString(1, courseID);
                        try (ResultSet rc = pc.executeQuery()) {
                            if (rc.next()) course = rc.getString("CourseName");
                        }
                    } catch (SQLException ignored) {}
                    // try get subject name (first subject for course)
                    try (PreparedStatement ps = conn.prepareStatement("SELECT SubName FROM tblsubject WHERE CourseID = ? LIMIT 1")) {
                        ps.setString(1, courseID);
                        try (ResultSet rs2 = ps.executeQuery()) {
                            if (rs2.next()) subject = rs2.getString("SubName");
                        }
                    } catch (SQLException ignored) {}
                }
                return new StudentRow(studID, fname, mname, lname, subject, course, year, section);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Get CourseID (as String) for a student.
     */
    public String getCourseIDForStudent(String studID) {
        if (conn == null || studID == null || studID.isEmpty()) return null;
        String sql = "SELECT CourseID FROM tblstudent WHERE StudID = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, studID);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getString("CourseID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // -----------------------------
    // Update student
    // -----------------------------
    /**
     * Update student core fields and the related course/subject names if CourseID exists.
     * Returns true on success. May throw SQLException to caller if desired - currently catches and returns false.
     */
    public boolean updateStudent(String studID, StudentRow updated) throws SQLException {
        if (conn == null) throw new SQLException("No DB connection");
        if (studID == null || studID.isEmpty() || updated == null) throw new SQLException("Invalid parameters");

        String sqlStudent = "UPDATE tblstudent SET FName=?, MName=?, LName=?, Year=?, Section=? WHERE StudID = ?";
        try (PreparedStatement pst = conn.prepareStatement(sqlStudent)) {
            pst.setString(1, updated.fname);
            pst.setString(2, updated.mname);
            pst.setString(3, updated.lname);
            pst.setString(4, updated.year);
            pst.setString(5, updated.section);
            pst.setString(6, studID);
            pst.executeUpdate();
        }

        // update course/subject names if a course exists for that student
        String courseID = getCourseIDForStudent(studID);
        if (courseID != null && !courseID.isEmpty()) {
            try (PreparedStatement pc = conn.prepareStatement("UPDATE tblcourse SET CourseName = ? WHERE CourseID = ?")) {
                pc.setString(1, updated.course == null ? "" : updated.course);
                pc.setString(2, courseID);
                pc.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("UPDATE tblsubject SET SubName = ? WHERE CourseID = ?")) {
                ps.setString(1, updated.subject == null ? "" : updated.subject);
                ps.setString(2, courseID);
                ps.executeUpdate();
            }
        }
        return true;
    }

    // -----------------------------
    // Deletes (attendance)
    // -----------------------------
    public boolean deleteAttendanceByStudID(String studID) {
        if (conn == null || studID == null || studID.isEmpty()) return false;
        try (PreparedStatement pst = conn.prepareStatement("DELETE FROM tblattendance WHERE StudID = ?")) {
            pst.setString(1, studID);
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Delete attendance rows for a student on a specific date.
     * If date==null deletes all attendance rows for that student.
     */
    public boolean deleteAttendanceForStudOnDate(String studID, java.sql.Date date) {
        if (conn == null || studID == null || studID.isEmpty()) return false;
        String sql = (date == null)
                ? "DELETE FROM tblattendance WHERE StudID = ?"
                : "DELETE FROM tblattendance WHERE StudID = ? AND DATE(TimeStamp) = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, studID);
            if (date != null) pst.setDate(2, date);
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Delete a single attendance record by its primary key (AttendID).
     */
    public boolean deleteAttendanceById(String attendId) {
        if (conn == null || attendId == null || attendId.isEmpty()) return false;
        String sql = "DELETE FROM tblattendance WHERE AttendID = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, attendId);
            int affected = pst.executeUpdate();
            return affected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // -----------------------------
    // Attendance lookup helpers
    // -----------------------------
    /**
     * Try to find StudID by matching attendance rows joined with student/course/subject.
     * Returns empty string if not found.
     *
     * NOTE: this is a best-effort helper for UIs that don't include StudID in the model.
     * Prefer storing attendance PK (AttendID) and deleting by PK when possible.
     */
    public String findStudIDFromAttendanceRow(StudentRow row, String courseName, String subjectName, java.sql.Date date) {
        if (conn == null || row == null) return "";
        String sql =
            "SELECT a.StudID " +
            "FROM tblattendance a " +
            "JOIN tblstudent st ON a.StudID = st.StudID " +
            "LEFT JOIN tblschedule sch ON a.SchedID = sch.SchedID " +
            "LEFT JOIN tblsubject sub ON sch.SubID = sub.SubID " +
            "LEFT JOIN tblcourse c ON sub.CourseID = c.CourseID " +
            "WHERE st.LName = ? AND st.FName = ? AND st.MName = ? " +
            "  AND st.Year = ? AND st.Section = ? " +
            "  AND (? IS NULL OR c.CourseName = ?) " +
            "  AND (? IS NULL OR sub.SubName = ?) " +
            "  AND (? IS NULL OR DATE(a.TimeStamp) = ?) " +
            "LIMIT 1";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, row.lname);
            pst.setString(2, row.fname);
            pst.setString(3, row.mname);
            pst.setString(4, row.year);
            pst.setString(5, row.section);

            if (courseName == null || courseName.isEmpty()) { pst.setNull(6, Types.VARCHAR); pst.setNull(7, Types.VARCHAR); }
            else { pst.setString(6, courseName); pst.setString(7, courseName); }

            if (subjectName == null || subjectName.isEmpty()) { pst.setNull(8, Types.VARCHAR); pst.setNull(9, Types.VARCHAR); }
            else { pst.setString(8, subjectName); pst.setString(9, subjectName); }

            if (date == null) { pst.setNull(10, Types.DATE); pst.setNull(11, Types.DATE); }
            else { pst.setDate(10, date); pst.setDate(11, date); }

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getString("StudID");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * Find attendance rows that match the supplied visible row data.
     * Returns a list (possibly empty) of AttendanceRecord objects.
     *
     * If date is null, the SQL does not filter by date (wider search).
     * Use java.sql.Date.valueOf(LocalDate.now()) to restrict to today's attendance.
     */
    public List<AttendanceRecord> findAttendanceMatches(StudentRow row, String courseName, String subjectName, java.sql.Date date) {
        List<AttendanceRecord> out = new ArrayList<>();
        if (conn == null || row == null) return out;

        String sql =
            "SELECT a.AttendID AS AttendID, a.StudID AS StudID, sch.SchedID AS SchedID, a.TimeStamp AS TimeStamp, a.Status AS Status, " +
            "       c.CourseName AS CourseName, sub.SubName AS SubName, st.FName AS FName, st.MName AS MName, st.LName AS LName, st.Year AS Year, st.Section AS Section " +
            "FROM tblattendance a " +
            "JOIN tblstudent st ON a.StudID = st.StudID " +
            "LEFT JOIN tblschedule sch ON a.SchedID = sch.SchedID " +
            "LEFT JOIN tblsubject sub ON sch.SubID = sub.SubID " +
            "LEFT JOIN tblcourse c ON sub.CourseID = c.CourseID " +
            "WHERE st.LName = ? AND st.FName = ? AND st.MName = ? " +
            "  AND st.Year = ? AND st.Section = ? " +
            "  AND (? IS NULL OR c.CourseName = ?) " +
            "  AND (? IS NULL OR sub.SubName = ?) " +
            "  AND (? IS NULL OR DATE(a.TimeStamp) = ?) " +
            "ORDER BY a.TimeStamp DESC";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, row.lname);
            pst.setString(2, row.fname);
            pst.setString(3, row.mname);
            pst.setString(4, row.year);
            pst.setString(5, row.section);

            if (courseName == null || courseName.isEmpty()) {
                pst.setNull(6, Types.VARCHAR); pst.setNull(7, Types.VARCHAR);
            } else {
                pst.setString(6, courseName); pst.setString(7, courseName);
            }

            if (subjectName == null || subjectName.isEmpty()) {
                pst.setNull(8, Types.VARCHAR); pst.setNull(9, Types.VARCHAR);
            } else {
                pst.setString(8, subjectName); pst.setString(9, subjectName);
            }

            if (date == null) {
                pst.setNull(10, Types.DATE); pst.setNull(11, Types.DATE);
            } else {
                pst.setDate(10, date); pst.setDate(11, date);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String attendId = rs.getString("AttendID");
                    String studID = rs.getString("StudID");
                    String schedID = rs.getString("SchedID");
                    Timestamp ts = rs.getTimestamp("TimeStamp");
                    String status = rs.getString("Status");
                    String cName = rs.getString("CourseName");
                    String sName = rs.getString("SubName");
                    String f = rs.getString("FName");
                    String m = rs.getString("MName");
                    String l = rs.getString("LName");
                    String y = rs.getString("Year");
                    String sec = rs.getString("Section");

                    AttendanceRecord ar = new AttendanceRecord(attendId, studID, schedID, ts, status, cName, sName, f, m, l, y, sec);
                    out.add(ar);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return out;
    }
}