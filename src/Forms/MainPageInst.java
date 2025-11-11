/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.opencv.core.Core;



/**
 *
 * @author Jobelle
 */
/**
 * Main page for instructor.
 */
public class MainPageInst extends BaseFrame {

    // static initializer
    static {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("OpenCV loaded successfully via System.loadLibrary: " + Core.NATIVE_LIBRARY_NAME);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("System.loadLibrary failed: " + e.getMessage());
            try {
                String path = DBConfig.getOpenCvDllPath();
                if (path != null && !path.isEmpty()) {
                    System.load(path);
                    System.out.println("OpenCV loaded via absolute path: " + path);
                } else {
                    System.err.println("DBConfig OpenCV path is empty. Native library not loaded.");
                }
            } catch (Throwable ex) {
                System.err.println("Failed to load OpenCV native library from DBConfig path: " + ex.getMessage());
            }
        }
    }

    private HomePage homePage;
    
    // Camera manager object
    private CameraManager cameraManager;
    
// Keep single instances to reuse across navigation
    private AttendanceLogInst attendanceLogInst;
    private ViewClasses viewClasses;

    // Logged-in user information
    private String InstID;
    private String Role;
    private String FName;
    private String MName;
    private String LName;

    /**
     * Creates new form HomePage
     * @param homePage
     */
    public MainPageInst(HomePage homePage) {
        super(homePage);
        this.homePage = homePage;
        initComponents();
        setCenter();
        useCustomBackground();
        setVisible(true);
        cameraManager = null;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (cameraManager != null) {
                    cameraManager.stopCamera();
                    cameraManager.releaseCamera();
                }
            }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        newButton1 = new customElements.NewButton();
        newButton2 = new customElements.NewButton();
        BackHome = new javax.swing.JLabel();
        Logout = new customElements.NewButton();
        btnViewProfile = new customElements.NewButton();
        CameraFrame = new javax.swing.JPanel();
        Camera = new customElements.Panel();
        jLabel1 = new javax.swing.JLabel();
        DisplayLog = new javax.swing.JLabel();
        BAttendanceLog = new customElements.NewButton();
        BViewClass = new customElements.NewButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 700));

        jPanel1.setBackground(new java.awt.Color(40, 17, 84));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 700));

        newButton1.setText("Start");
        newButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButton1ActionPerformed(evt);
            }
        });

        newButton2.setText("Stop");
        newButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButton2ActionPerformed(evt);
            }
        });

        BackHome.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        BackHome.setForeground(new java.awt.Color(255, 255, 255));
        BackHome.setText("< Home");
        BackHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackHomeMouseClicked(evt);
            }
        });

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        btnViewProfile.setText("View Profile");
        btnViewProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewProfileActionPerformed(evt);
            }
        });

        CameraFrame.setBackground(new java.awt.Color(40, 17, 84));

        Camera.setBackground(new java.awt.Color(255, 255, 255));
        Camera.setPreferredSize(new java.awt.Dimension(420, 240));

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Camera is turned off.");

        javax.swing.GroupLayout CameraLayout = new javax.swing.GroupLayout(Camera);
        Camera.setLayout(CameraLayout);
        CameraLayout.setHorizontalGroup(
            CameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
        );
        CameraLayout.setVerticalGroup(
            CameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CameraLayout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(jLabel1)
                .addContainerGap(201, Short.MAX_VALUE))
        );

        DisplayLog.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        DisplayLog.setForeground(new java.awt.Color(255, 255, 255));
        DisplayLog.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DisplayLog.setText("Display Attendance Log");

        javax.swing.GroupLayout CameraFrameLayout = new javax.swing.GroupLayout(CameraFrame);
        CameraFrame.setLayout(CameraFrameLayout);
        CameraFrameLayout.setHorizontalGroup(
            CameraFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CameraFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CameraFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CameraFrameLayout.createSequentialGroup()
                        .addComponent(Camera, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addComponent(DisplayLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        CameraFrameLayout.setVerticalGroup(
            CameraFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CameraFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Camera, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DisplayLog)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BAttendanceLog.setText("Attendance Log");
        BAttendanceLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BAttendanceLogActionPerformed(evt);
            }
        });

        BViewClass.setText("View Classes");
        BViewClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BViewClassActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("QR Scanner");

        jLabel3.setForeground(new java.awt.Color(37, 99, 235));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("________________________________________________________________________________________________________________________________________________");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BAttendanceLog, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(BViewClass, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnViewProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(242, 242, 242))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 974, Short.MAX_VALUE)
                .addGap(26, 26, 26))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(238, 238, 238)
                        .addComponent(newButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(newButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CameraFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BackHome))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BAttendanceLog, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(BViewClass, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BackHome)
                .addGap(18, 18, 18)
                .addComponent(CameraFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        boolean logoutClicked = true;

        if (logoutClicked){
            this.dispose();

            LoginForm LF = new LoginForm();
            LF.setVisible(true);
        }
    }//GEN-LAST:event_LogoutActionPerformed

    private void btnViewProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewProfileActionPerformed
        dialogViewProfile dvp = new dialogViewProfile(this, true);
        dvp.setLoggedInUser(this.InstID, this.Role, this.FName, this.MName, this.LName);
        dvp.setVisible(true);
    }//GEN-LAST:event_btnViewProfileActionPerformed

    private void BViewClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BViewClassActionPerformed
        ViewClasses VC = homePage.getViewClasses(); // reuse existing instance
        VC.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_BViewClassActionPerformed

    private void BAttendanceLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAttendanceLogActionPerformed
        AttendanceLogInst AL = homePage.getAttendanceLogInst(); // reuse existing instance
        AL.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_BAttendanceLogActionPerformed

    private void newButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButton2ActionPerformed
        if (cameraManager != null) {
            cameraManager.stopCamera();
            cameraManager.releaseCamera(); // <--- make sure this method releases VideoCapture
            cameraManager = null; // allows clean restart
        }
    }//GEN-LAST:event_newButton2ActionPerformed

    private void newButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButton1ActionPerformed
        jLabel1.setVisible(false);
        if (cameraManager == null) {
            cameraManager = new CameraManager(Camera, DisplayLog, this);
            cameraManager.startCamera();
        }
    }//GEN-LAST:event_newButton1ActionPerformed

    private void BackHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackHomeMouseClicked
        this.setVisible(false);
        homePage.setVisible(true);
    }//GEN-LAST:event_BackHomeMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HomePage homePage = new HomePage();
                MainPageInst MP = new MainPageInst(homePage);
                MP.setVisible(true);
            }
        });
    }
    
    public void hideCameraLabel() {
        jLabel1.setVisible(false);
    }
    
    public void setLoggedInUser(String InstID, String Role, String FName, String MName, String LName) {
        this.InstID = InstID;
        this.Role = Role;
        this.FName = FName;
        this.MName = MName;
        this.LName = LName;
        //displayUserInfo();
    }
   
    private void setCenter() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int left = (d.width - this.getWidth()) / 2;
        int top = (d.height - this.getHeight()) / 2;
        this.setLocation(left, top);
    }
    
    private void useCustomBackground() {
        try {
            // Load background image
            final java.awt.image.BufferedImage bg = javax.imageio.ImageIO.read(
                getClass().getResource("/images/headerQRrive.jpg")
            );

            javax.swing.JPanel bgPanel = new javax.swing.JPanel(new java.awt.BorderLayout()) {
                @Override
                protected void paintComponent(java.awt.Graphics g) {
                    super.paintComponent(g);
                    if (bg == null) return;

                    java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                    try {
                        // High quality rendering hints
                        g2.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                                            java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                        g2.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                                            java.awt.RenderingHints.VALUE_RENDER_QUALITY);
                        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                                            java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                        // Draw the background image scaled to fill the panel (maintain aspect if you prefer)
                        int w = getWidth();
                        int h = getHeight();
                        g2.drawImage(bg, 0, 0, w, h, this);
                    } finally {
                        g2.dispose();
                    }
                }
            };

            jPanel1.setOpaque(false);
            bgPanel.add(jPanel1, java.awt.BorderLayout.CENTER);

            setContentPane(bgPanel);
            pack(); // resize window correctly
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        /**
     * Synchronous (blocking) DB work that processes the QR and returns a result object.
     * This method MUST be called off the EDT (i.e. from a background thread).
     */
    public AttendanceResult processAttendanceSync(String qrText) {
        AttendanceResult result = new AttendanceResult("", JOptionPane.INFORMATION_MESSAGE);
        try (Connection conn = DBConfig.getConnection()) {

            String studID = qrText == null ? "" : qrText.trim();

            if (studID.isEmpty()) {
                result.message = "Invalid QR code!";
                result.messageType = JOptionPane.ERROR_MESSAGE;
                return result;
            }

            // 1. Check if student exists
            try (PreparedStatement psStudent = conn.prepareStatement("SELECT * FROM tblstudent WHERE StudID = ?")) {
                psStudent.setString(1, studID);
                try (ResultSet rsStudent = psStudent.executeQuery()) {
                    if (!rsStudent.next()) {
                        result.message = "This student does not exist.";
                        result.messageType = JOptionPane.ERROR_MESSAGE;
                        return result;
                    }

                    // 2. Find current schedule for instructor
                    try (PreparedStatement psSchedule = conn.prepareStatement(
                            "SELECT * FROM tblschedule WHERE InstID = ? AND DayOfWeek = DAYNAME(CURDATE()) " +
                                    "AND CURTIME() BETWEEN Start AND End LIMIT 1")) {
                        psSchedule.setString(1, this.InstID);
                        try (ResultSet rsSchedule = psSchedule.executeQuery()) {
                            if (!rsSchedule.next()) {
                                result.message = "You have no class this time around.";
                                result.messageType = JOptionPane.INFORMATION_MESSAGE;
                                return result;
                            }

                            String schedID = rsSchedule.getString("SchedID");
                            java.time.LocalTime startTime = rsSchedule.getTime("Start").toLocalTime();
                            java.time.LocalTime endTime = rsSchedule.getTime("End").toLocalTime();
                            java.time.LocalTime now = java.time.LocalTime.now();

                            String status;
                            if (now.isBefore(startTime.plusMinutes(15))) {
                                status = "Present";
                            } else if (now.isBefore(endTime)) {
                                status = "Late";
                            } else {
                                status = "Absent";
                            }

                            // 4. Check if attendance already exists
                            try (PreparedStatement psCheck = conn.prepareStatement(
                                    "SELECT * FROM tblattendance WHERE StudID = ? AND SchedID = ? AND DATE(TimeStamp) = CURDATE()")) {
                                psCheck.setString(1, studID);
                                psCheck.setString(2, schedID);
                                try (ResultSet rsCheck = psCheck.executeQuery()) {
                                    if (rsCheck.next()) {
                                        String msg = "Attendance already recorded for today for " +
                                                rsStudent.getString("FName") + " " + rsStudent.getString("LName");
                                        result.message = msg;
                                        result.messageType = JOptionPane.WARNING_MESSAGE;
                                        return result;
                                    } else {
                                        // 5. Insert attendance
                                        try (PreparedStatement psInsert = conn.prepareStatement(
                                                "INSERT INTO tblattendance (StudID, Status, DayOfWeek, TimeStamp, SchedID) " +
                                                        "VALUES (?, ?, DAYNAME(CURDATE()), NOW(), ?)")) {
                                            psInsert.setString(1, studID);
                                            psInsert.setString(2, status);
                                            psInsert.setString(3, schedID);
                                            psInsert.executeUpdate();
                                        }

                                        String msg = "Attendance recorded for " + rsStudent.getString("FName") + " " +
                                                rsStudent.getString("LName") + " as " + status;
                                        result.message = msg;
                                        result.messageType = JOptionPane.INFORMATION_MESSAGE;
                                        return result;
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.message = "Error scanning QR: " + e.getMessage();
            result.messageType = JOptionPane.ERROR_MESSAGE;
        }
        return result;
    }

    /**
     * Legacy public method used by UI callers: runs the synchronous processor on
     * a background thread and shows dialog on the EDT (keeps DB off the EDT).
     */
    public void logAttendance(String qrText) {
        new Thread(() -> {
            AttendanceResult res = processAttendanceSync(qrText);
            SwingUtilities.invokeLater(() -> {
                if (res != null && res.message != null && !res.message.isEmpty()) {
                    JOptionPane.showMessageDialog(this, res.message, "Scan Result", res.messageType);
                }
            });
        }, "Attendance-Worker").start();
    }
    
     //Small return type to carry a message and JOptionPane message type.
    public static class AttendanceResult {
        public String message;
        public int messageType;

        public AttendanceResult(String message, int messageType) {
            this.message = message;
            this.messageType = messageType;
        }
    }
    
    public String getCurrentScheduleInfo() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/classattendance", "root", ""
            );

            PreparedStatement psSchedule = conn.prepareStatement(
                "SELECT s.SchedID, s.Classroom, s.SubID " +
                "FROM tblschedule s " +
                "WHERE s.InstID = ? AND s.DayOfWeek = DAYNAME(CURDATE()) " +
                "AND CURTIME() BETWEEN s.Start AND s.End " +
                "LIMIT 1"
            );
            psSchedule.setString(1, this.InstID);
            ResultSet rs = psSchedule.executeQuery();

            String info = "None";
            if (rs.next()) {
                info = rs.getString("SchedID") + " | Sub: " + rs.getString("SubID") +
                       " | Room: " + rs.getString("Classroom");
            }

            rs.close();
            psSchedule.close();
            conn.close();

            return info;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching schedule";
        }
    }

    // markAbsentAfterClass()
    public void markAbsentAfterClass() {
        new Thread(() -> {
            try (Connection conn = DBConfig.getConnection()) {
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO tblattendance (StudID, Status, DayOfWeek, TimeStamp, SchedID) " +
                                "SELECT s.StudID, 'Absent', DAYNAME(CURDATE()), NOW(), sc.SchedID " +
                                "FROM tblstudent s " +
                                "JOIN tblschedule sc ON sc.InstID = ? " +
                                "WHERE sc.DayOfWeek = DAYNAME(CURDATE()) " +
                                "AND CURTIME() > sc.End " +
                                "AND s.StudID NOT IN (" +
                                "  SELECT StudID FROM tblattendance WHERE SchedID = sc.SchedID AND DATE(TimeStamp) = CURDATE()" +
                                ")"
                )) {
                    ps.setString(1, this.InstID);
                    ps.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    public String getLoggedInInstID() {
        return this.InstID;
    }
    
    public String getInstructorID() {
        return InstID;
    }
    
    public void setInstructorID(String instID) {
        this.InstID = instID;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private customElements.NewButton BAttendanceLog;
    private customElements.NewButton BViewClass;
    private javax.swing.JLabel BackHome;
    private customElements.Panel Camera;
    private javax.swing.JPanel CameraFrame;
    private javax.swing.JLabel DisplayLog;
    private customElements.NewButton Logout;
    private customElements.NewButton btnViewProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private customElements.NewButton newButton1;
    private customElements.NewButton newButton2;
    // End of variables declaration//GEN-END:variables

}
