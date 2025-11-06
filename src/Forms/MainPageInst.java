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
     * Creates new form MainPageInst
     */
    public MainPageInst() {
        super(null);
        initComponents();
        setCenter();
        useCustomBackground();
        setVisible(true);
        cameraManager = null;

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (cameraManager != null) cameraManager.stopCamera();
            }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        CameraFrame = new customElements.Panel();
        Camera = new customElements.Panel();
        jLabel1 = new javax.swing.JLabel();
        DisplayLog = new javax.swing.JLabel();
        labelScan = new javax.swing.JLabel();
        panelNavigation = new javax.swing.JPanel();
        DisplayInfo = new javax.swing.JLabel();
        BAttendanceLog = new customElements.NewButton();
        BViewClass = new customElements.NewButton();
        BViewProfile = new customElements.NewButton();
        Logout = new customElements.NewButton();
        IDScanner = new customElements.NewButton();
        newButton1 = new customElements.NewButton();
        newButton2 = new customElements.NewButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 700));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1100, 700));

        CameraFrame.setBackground(new java.awt.Color(255, 255, 255));

        Camera.setBackground(new java.awt.Color(255, 255, 255));
        Camera.setPreferredSize(new java.awt.Dimension(420, 240));

        jLabel1.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        jLabel1.setText("Camera is turned off.");

        javax.swing.GroupLayout CameraLayout = new javax.swing.GroupLayout(Camera);
        Camera.setLayout(CameraLayout);
        CameraLayout.setHorizontalGroup(
            CameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CameraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(207, 207, 207))
        );
        CameraLayout.setVerticalGroup(
            CameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CameraLayout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addComponent(jLabel1)
                .addContainerGap(211, Short.MAX_VALUE))
        );

        DisplayLog.setBackground(new java.awt.Color(255, 255, 255));
        DisplayLog.setFont(new java.awt.Font("Poppins Medium", 0, 10)); // NOI18N
        DisplayLog.setForeground(new java.awt.Color(17, 24, 39));
        DisplayLog.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DisplayLog.setText("Scanning QR Code...");

        javax.swing.GroupLayout CameraFrameLayout = new javax.swing.GroupLayout(CameraFrame);
        CameraFrame.setLayout(CameraFrameLayout);
        CameraFrameLayout.setHorizontalGroup(
            CameraFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CameraFrameLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(CameraFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Camera, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                    .addComponent(DisplayLog, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))
                .addGap(29, 29, 29))
        );
        CameraFrameLayout.setVerticalGroup(
            CameraFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CameraFrameLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(Camera, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(DisplayLog)
                .addContainerGap())
        );

        labelScan.setBackground(new java.awt.Color(255, 255, 255));
        labelScan.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        labelScan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelScan.setText("Scan ID number here");

        panelNavigation.setBackground(new java.awt.Color(15, 23, 42));

        DisplayInfo.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        DisplayInfo.setForeground(new java.awt.Color(255, 255, 255));
        DisplayInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DisplayInfo.setText("DisplayInfo");

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

        BViewProfile.setText("View Profile");
        BViewProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BViewProfileActionPerformed(evt);
            }
        });

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelNavigationLayout = new javax.swing.GroupLayout(panelNavigation);
        panelNavigation.setLayout(panelNavigationLayout);
        panelNavigationLayout.setHorizontalGroup(
            panelNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNavigationLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(panelNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BViewClass, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(BAttendanceLog, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(BViewProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelNavigationLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(DisplayInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        panelNavigationLayout.setVerticalGroup(
            panelNavigationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNavigationLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(DisplayInfo)
                .addGap(18, 18, 18)
                .addComponent(BAttendanceLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BViewClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BViewProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        IDScanner.setText("ID Scanner");
        IDScanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDScannerActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelNavigation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IDScanner, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CameraFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelScan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(newButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(newButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(278, 278, 278))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(IDScanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(labelScan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CameraFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
            .addComponent(panelNavigation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BAttendanceLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BAttendanceLogActionPerformed
        AttendanceLogInst AL = new AttendanceLogInst(this);
        AL.setLoggedInUser(InstID, Role, FName, MName, LName);
        AL.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_BAttendanceLogActionPerformed

    private void BViewClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BViewClassActionPerformed
        ViewClasses VC = new ViewClasses (this);
        VC.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_BViewClassActionPerformed

    private void IDScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDScannerActionPerformed

    }//GEN-LAST:event_IDScannerActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        boolean logoutClicked = true;
        
        if (logoutClicked){
            this.dispose();
            
            LoginForm LF = new LoginForm();
            LF.setVisible(true);
        }
    }//GEN-LAST:event_LogoutActionPerformed

    private void BViewProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BViewProfileActionPerformed
        ViewProfile viewProf = new ViewProfile(this);
        viewProf.setLoggedInUser(InstID, Role, FName, MName, LName);
        viewProf.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_BViewProfileActionPerformed

    private void newButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButton1ActionPerformed
        jLabel1.setVisible(false);
        if (cameraManager == null) {
            cameraManager = new CameraManager(Camera, DisplayLog, this);
            cameraManager.startCamera();
        }
    }//GEN-LAST:event_newButton1ActionPerformed

    private void newButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButton2ActionPerformed
        if (cameraManager != null) {
            cameraManager.stopCamera();
            cameraManager.releaseCamera(); // <--- make sure this method releases VideoCapture
            cameraManager = null; // allows clean restart
        }
    }//GEN-LAST:event_newButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainPageInst.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPageInst.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPageInst.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPageInst.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPageInst().setVisible(true);
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
        displayUserInfo();
    }
    
    private void displayUserInfo() {
        String info = LName.toUpperCase() + ", " + FName + " " + "\nRole: " + Role;
        if ("Instructor".equalsIgnoreCase(Role)) {
            info += "\nID: " + InstID;
        }
        DisplayInfo.setText("<html>" + info.replace("\n", "<br>") + "</html>");
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
            java.awt.image.BufferedImage bg = javax.imageio.ImageIO.read(
                getClass().getResource("/images/Option4.png")
            );

            ImageRenderComponent bgPanel = new ImageRenderComponent(bg);
            bgPanel.setLayout(new java.awt.BorderLayout());
            jPanel1.setOpaque(false);
            bgPanel.add(jPanel1, java.awt.BorderLayout.CENTER);
            
            setContentPane(bgPanel);
            pack(); // resize window correctly
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // logAttendance(String qrText)
    public void logAttendance(String qrText) {
        try (Connection conn = DBConfig.getConnection()) {

            String studID = qrText.trim(); // QR contains the StudID

            if (studID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid QR code!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Check if student exists
            try (PreparedStatement psStudent = conn.prepareStatement("SELECT * FROM tblstudent WHERE StudID = ?")) {
                psStudent.setString(1, studID);
                try (ResultSet rsStudent = psStudent.executeQuery()) {
                    if (!rsStudent.next()) {
                        JOptionPane.showMessageDialog(this, "This student does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // 2. Find current schedule for instructor
                    try (PreparedStatement psSchedule = conn.prepareStatement(
                            "SELECT * FROM tblschedule WHERE InstID = ? AND DayOfWeek = DAYNAME(CURDATE()) " +
                                    "AND CURTIME() BETWEEN Start AND End LIMIT 1")) {
                        psSchedule.setString(1, this.InstID);
                        try (ResultSet rsSchedule = psSchedule.executeQuery()) {
                            if (!rsSchedule.next()) {
                                JOptionPane.showMessageDialog(this, "You have no class this time around.", "Info", JOptionPane.INFORMATION_MESSAGE);
                                return;
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
                                        JOptionPane.showMessageDialog(this,
                                                "Attendance already recorded for today for " +
                                                        rsStudent.getString("FName") + " " + rsStudent.getString("LName"),
                                                "Duplicate Entry",
                                                JOptionPane.WARNING_MESSAGE
                                        );
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

                                        JOptionPane.showMessageDialog(this,
                                                "Attendance recorded for " + rsStudent.getString("FName") + " " +
                                                        rsStudent.getString("LName") + " as " + status,
                                                "Success",
                                                JOptionPane.INFORMATION_MESSAGE
                                        );
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error scanning QR: " + e.getMessage());
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
    
    // EDIT: method to get (and reuse) that single instance
    public AttendanceLogInst getAttendanceLogInst() {
        if (attendanceLogInst == null) {
            attendanceLogInst = new AttendanceLogInst(this);
            attendanceLogInst.setLoggedInUser(getInstructorID(), Role, FName, MName, LName);
        }
        return attendanceLogInst;
    }

    public ViewClasses getViewClasses() {
        if (viewClasses == null) {
            viewClasses = new ViewClasses(this);
            viewClasses.setLoggedInUser(getInstructorID(), Role, FName, MName, LName);
        }
        return viewClasses;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private customElements.NewButton BAttendanceLog;
    private customElements.NewButton BViewClass;
    private customElements.NewButton BViewProfile;
    private customElements.Panel Camera;
    private customElements.Panel CameraFrame;
    private javax.swing.JLabel DisplayInfo;
    private javax.swing.JLabel DisplayLog;
    private customElements.NewButton IDScanner;
    private customElements.NewButton Logout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelScan;
    private customElements.NewButton newButton1;
    private customElements.NewButton newButton2;
    private javax.swing.JPanel panelNavigation;
    // End of variables declaration//GEN-END:variables

}
