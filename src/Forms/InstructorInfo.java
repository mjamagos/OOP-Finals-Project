package Forms;

/**
 * Small immutable holder for instructor profile values.
 */
public final class InstructorInfo {
    public final String instId;
    public final String fname;
    public final String mname;
    public final String lname;
    public final String email;
    public final String address;
    public final String department;
    public final String college;
    public final String university;

    public InstructorInfo(String instId,
                          String fname,
                          String mname,
                          String lname,
                          String email,
                          String address,
                          String department,
                          String college,
                          String university) {
        this.instId = instId;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.email = email;
        this.address = address;
        this.department = department;
        this.college = college;
        this.university = university;
    }
}