package Forms;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * StudentRepo — single place for student/attendance related DB operations.
 *
 * Safety:
 *  - updateStudent() only updates tblstudent columns.
 *  - updateStudentFields() updates only whitelisted tblstudent columns.
 *  - No code here will update tblcourse or tblsubject.
 */
public class StudentRepo {
    private final Connection conn;

    public StudentRepo(Connection conn) {
        this.conn = conn;
    }

    // -----------------------------
    // Student lookups / fetch
    // -----------------------------
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
                    // best-effort read - do NOT write here
                    try (PreparedStatement pc = conn.prepareStatement("SELECT CourseName FROM tblcourse WHERE CourseID = ? LIMIT 1")) {
                        pc.setString(1, courseID);
                        try (ResultSet rc = pc.executeQuery()) {
                            if (rc.next()) course = rc.getString("CourseName");
                        }
                    } catch (SQLException ignored) {}
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
    // Update student (safe)
    // -----------------------------
    /**
     * Legacy method: updates only the tblstudent fields for the given StudID.
     * Intentionally does NOT write to tblcourse/tblsubject.
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
            int affected = pst.executeUpdate();
            return affected > 0;
        }
    }

    /**
     * Update only the specified student columns for the given StudID.
     * fields keys must be column names from tblstudent; only the safe whitelist is allowed.
     */
    public boolean updateStudentFields(String studID, Map<String, String> fields) throws SQLException {
        if (conn == null) throw new SQLException("No DB connection");
        if (studID == null || studID.isEmpty()) throw new SQLException("Invalid StudID");
        if (fields == null || fields.isEmpty()) return true; // nothing to do

        List<String> allowed = List.of("FName", "MName", "LName", "Year", "Section");

        List<String> cols = new ArrayList<>();
        for (String k : fields.keySet()) {
            if (allowed.contains(k)) cols.add(k);
        }
        if (cols.isEmpty()) return true;

        StringBuilder sb = new StringBuilder("UPDATE tblstudent SET ");
        for (int i = 0; i < cols.size(); i++) {
            sb.append(cols.get(i)).append(" = ?");
            if (i < cols.size() - 1) sb.append(", ");
        }
        sb.append(" WHERE StudID = ?");

        try (PreparedStatement pst = conn.prepareStatement(sb.toString())) {
            int idx = 1;
            for (String col : cols) {
                pst.setString(idx++, fields.get(col));
            }
            pst.setString(idx, studID);
            int affected = pst.executeUpdate();
            return affected > 0;
        }
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
    // Attendance lookup helpers (unchanged)
    // -----------------------------
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

    public InstructorInfo fetchInstructorByID(String instID) {
        if (conn == null || instID == null || instID.isEmpty()) return null;

        String sql = "SELECT * FROM tblinstructor WHERE InstID = ? LIMIT 1";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, instID);
            try (ResultSet rs = pst.executeQuery()) {
                if (!rs.next()) return null;

                String fname = safeGet(rs, "FName");
                String mname = safeGet(rs, "MName");
                String lname = safeGet(rs, "LName");
                String email = safeGet(rs, "Email");
                if ((email == null || email.isEmpty())) email = safeGet(rs, "EmailAddress");
                String address = safeGet(rs, "Address");
                String college = safeGet(rs, "College");

                String collegeId = safeGet(rs, "CollegeID");
                if ((college == null || college.isEmpty()) && collegeId != null && !collegeId.isEmpty()) {
                    try (PreparedStatement pc = conn.prepareStatement("SELECT CollegeName FROM tblcollege WHERE CollegeID = ? LIMIT 1")) {
                        pc.setString(1, collegeId);
                        try (ResultSet rc = pc.executeQuery()) {
                            if (rc.next()) college = rc.getString(1);
                        }
                    } catch (Exception ignored) {}
                }

                String department = safeGet(rs, "Department");
                if ((department == null || department.isEmpty())) {
                    String[] depIdCols = { "DepID", "depId", "departmentid", "DepartmentID", "Dep_Id" };
                    for (String c : depIdCols) {
                        String val = safeGet(rs, c);
                        if (val != null && !val.isEmpty()) {
                            try (PreparedStatement pd = conn.prepareStatement("SELECT depName FROM tbldepartment WHERE depId = ? LIMIT 1")) {
                                pd.setString(1, val);
                                try (ResultSet rd = pd.executeQuery()) {
                                    if (rd.next()) {
                                        department = rd.getString(1);
                                        break;
                                    }
                                }
                            } catch (Exception ignored) {}
                        }
                    }
                }

                String university = "University of Northern Philippines";
                return new InstructorInfo(instID, fname, mname, lname, email, address, department, college, university);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String safeGet(ResultSet rs, String col) {
        try {
            if (col == null || col.isEmpty()) return "";
            try {
                String v = rs.getString(col);
                return v == null ? "" : v;
            } catch (SQLException se) {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public List<String> listCoursesForInstructor(String instID) {
        List<String> out = new ArrayList<>();
        if (conn == null || instID == null || instID.isEmpty()) return out;
        String sql =
            "SELECT DISTINCT c.CourseName " +
            "FROM tblschedule sc " +
            "JOIN tblsubject sub ON sc.SubID = sub.SubID " +
            "JOIN tblcourse c ON sub.CourseID = c.CourseID " +
            "WHERE sc.InstID = ? " +
            "ORDER BY c.CourseName";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, instID);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) out.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return out;
    }

    public List<String> listSubjectsForInstructor(String instID, String courseName) {
        List<String> out = new ArrayList<>();
        if (conn == null || instID == null || instID.isEmpty()) return out;
        String sql =
            "SELECT DISTINCT sub.SubName " +
            "FROM tblschedule sc " +
            "JOIN tblsubject sub ON sc.SubID = sub.SubID " +
            "JOIN tblcourse c ON sub.CourseID = c.CourseID " +
            "WHERE sc.InstID = ? " +
            (courseName == null || courseName.isEmpty() ? "" : " AND c.CourseName = ? ") +
            "ORDER BY sub.SubName";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, instID);
            if (courseName != null && !courseName.isEmpty()) pst.setString(2, courseName);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) out.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return out;
    }

    public List<String> listYearsForInstructor(String instID) {
        List<String> out = new ArrayList<>();
        if (conn == null || instID == null || instID.isEmpty()) return out;
        String sql = "SELECT DISTINCT sc.Year FROM tblschedule sc WHERE sc.InstID = ? ORDER BY sc.Year";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, instID);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String y = rs.getString(1);
                    if (y != null && !y.isEmpty()) out.add(y);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return out;
    }

    public List<String> listSectionsForInstructor(String instID, String year) {
        List<String> out = new ArrayList<>();
        if (conn == null || instID == null || instID.isEmpty()) return out;
        String sql =
            "SELECT DISTINCT sc.Section FROM tblschedule sc WHERE sc.InstID = ? " +
            (year == null || year.isEmpty() ? "" : " AND sc.Year = ? ") +
            "ORDER BY sc.Section";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, instID);
            if (year != null && !year.isEmpty()) pst.setString(2, year);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    String s = rs.getString(1);
                    if (s != null && !s.isEmpty()) out.add(s);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return out;
    }
}