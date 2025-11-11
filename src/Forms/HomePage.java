/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Forms;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;


/**
 *
 * @author Jobelle
 */
public class HomePage extends BaseFrame {

    // Keep single instances to reuse across navigation
    private Connection conn;
    private StudentRepo dao;                // <- changed to StudentRepo
    private String loggedInInstID;
    private boolean integrationInitialized = false;
    
    public HomePage() {
        super(null);
        initComponents();
        setCenter();
        useCustomBackground();
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedComboBox1 = new customElements.RoundedComboBox();
        jPanel3 = new javax.swing.JPanel();
        BAttendanceLog = new customElements.NewButton();
        BViewClass = new customElements.NewButton();
        IDScanner = new customElements.NewButton();
        DisplayInfo = new javax.swing.JLabel();
        Logout = new customElements.NewButton();
        btnViewProfile = new customElements.NewButton();
        jLabel1 = new javax.swing.JLabel();
        generateQR = new javax.swing.JLabel();
        tfStudID = new customElements.NewTextField();
        btnGenerateQR = new customElements.NewButton();
        lblGenerateQR = new javax.swing.JLabel();
        cbCategory = new customElements.RoundedComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(null);

        jPanel3.setBackground(new java.awt.Color(40, 17, 84));
        jPanel3.setMaximumSize(null);
        jPanel3.setPreferredSize(new java.awt.Dimension(1000, 700));

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

        IDScanner.setText("ID Scanner");
        IDScanner.setToolTipText("");
        IDScanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDScannerActionPerformed(evt);
            }
        });

        DisplayInfo.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        DisplayInfo.setForeground(new java.awt.Color(255, 255, 255));
        DisplayInfo.setText("DisplayInfo");

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

        jLabel1.setForeground(new java.awt.Color(37, 99, 235));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("________________________________________________________________________________________________________________________________________________");

        generateQR.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        generateQR.setForeground(new java.awt.Color(255, 255, 255));
        generateQR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        generateQR.setText("<html><div style=\"text-align:center;\">Welcome to QRrive! <br> Effortless, accurate, and automated. QRrive streamlines attendance tracking by using unique student QR codes to log entry in seconds. Designed for instructors, it removes the hassle of manual checking so you can focus on what matters most</div></html>");

        tfStudID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfStudIDKeyReleased(evt);
            }
        });

        btnGenerateQR.setText("Generate QR");
        btnGenerateQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateQRActionPerformed(evt);
            }
        });

        lblGenerateQR.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        lblGenerateQR.setForeground(new java.awt.Color(255, 255, 255));
        lblGenerateQR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGenerateQR.setText("Generate QR Code");

        cbCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select by category", "Course", "Subject", "Student ID", " ", " " }));
        cbCategory.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(361, 361, 361)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfStudID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(btnGenerateQR, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(DisplayInfo))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(242, 242, 242)
                                .addComponent(IDScanner, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BAttendanceLog, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BViewClass, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnViewProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(168, 168, 168)
                                .addComponent(generateQR, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 157, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGenerateQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(389, 389, 389)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnViewProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BViewClass, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(IDScanner, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BAttendanceLog, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Logout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(generateQR, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblGenerateQR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(tfStudID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGenerateQR, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
                .addComponent(DisplayInfo)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1023, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        boolean logoutClicked = true;

        if (logoutClicked){
            this.dispose();

            LoginForm LF = new LoginForm();
            LF.setVisible(true);
        }
    }//GEN-LAST:event_LogoutActionPerformed

    private void IDScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDScannerActionPerformed
        MainPageInst MP = new MainPageInst(this);
        MP.setLoggedInUser(InstID, Role, FName, MName, LName);
        MP.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_IDScannerActionPerformed

    private void btnViewProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewProfileActionPerformed
        dialogViewProfile dvp = new dialogViewProfile(this, true);
        dvp.setLoggedInUser(this.InstID, this.Role, this.FName, this.MName, this.LName);
        dvp.setVisible(true);
    }//GEN-LAST:event_btnViewProfileActionPerformed

    private void btnGenerateQRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateQRActionPerformed
        String input = tfStudID.getText() == null ? "" : tfStudID.getText().trim();
        if (input.isEmpty() || "Enter Student ID".equalsIgnoreCase(input)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter a value first.", "No input", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        final String category = (cbCategory.getSelectedItem() == null) ? "Student ID" : cbCategory.getSelectedItem().toString().trim();
        final String outFolderPath = "C:\\Users\\Jobelle\\Downloads\\OOPProject\\QRCodes";

        new javax.swing.SwingWorker<String, Void>() {
            enum DuplicateAction { UNDECIDED, REPLACE_ALL, SKIP_ALL, CANCEL }
            private DuplicateAction dupAction = DuplicateAction.UNDECIDED;

            @Override
            protected String doInBackground() {
                java.util.List<String> ids = new java.util.ArrayList<>();
                java.util.Map<String, String> fullnameMap = new java.util.HashMap<>();

                // 1) Resolve student IDs based on category, restricted to instructor if InstID is present
                try (java.sql.Connection c = DBConfig.getConnection()) {
                    java.sql.PreparedStatement pst = null;
                    boolean restrict = InstID != null && !InstID.trim().isEmpty();

                    if ("Course".equalsIgnoreCase(category)) {
                        String sql = "SELECT DISTINCT s.StudID, s.FName, s.MName, s.LName " +
                                     "FROM tblstudent s " +
                                     "JOIN tblcourse c ON s.CourseID = c.CourseID " +
                                     "JOIN tblschedule sch ON sch.CourseID = s.CourseID AND sch.Year = s.Year AND sch.Section = s.Section " +
                                     "WHERE c.CourseName = ? " + (restrict ? "AND sch.InstID = ?" : "");
                        pst = c.prepareStatement(sql);
                        pst.setString(1, input);
                        if (restrict) pst.setString(2, InstID);
                    } else if ("Subject".equalsIgnoreCase(category)) {
                        String sql = "SELECT DISTINCT s.StudID, s.FName, s.MName, s.LName " +
                                     "FROM tblstudent s " +
                                     "JOIN tblschedule sch ON s.CourseID = sch.CourseID AND s.Year = sch.Year AND s.Section = sch.Section " +
                                     "JOIN tblsubject sub ON sch.SubID = sub.SubID " +
                                     "WHERE sub.SubName = ? " + (restrict ? "AND sch.InstID = ?" : "");
                        pst = c.prepareStatement(sql);
                        pst.setString(1, input);
                        if (restrict) pst.setString(2, InstID);
                    } else { // Student ID (treat input as a single StudID), verify instructor has that student if restrict
                        String sql = "SELECT s.StudID, s.FName, s.MName, s.LName " +
                                     "FROM tblstudent s ";
                        if (restrict) {
                            sql += "JOIN tblschedule sch ON s.CourseID = sch.CourseID AND s.Year = sch.Year AND s.Section = sch.Section ";
                            sql += "WHERE s.StudID = ? AND sch.InstID = ? LIMIT 1";
                            pst = c.prepareStatement(sql);
                            pst.setString(1, input);
                            pst.setString(2, InstID);
                        } else {
                            sql += "WHERE s.StudID = ? LIMIT 1";
                            pst = c.prepareStatement(sql);
                            pst.setString(1, input);
                        }
                    }

                    try (java.sql.ResultSet rs = pst.executeQuery()) {
                        while (rs.next()) {
                            String sid = rs.getString("StudID");
                            if (sid == null || sid.trim().isEmpty()) continue;
                            sid = sid.trim();
                            ids.add(sid);
                            String f = rs.getString("FName");
                            String m = rs.getString("MName");
                            String l = rs.getString("LName");
                            String fullname = ((f==null?"":f.trim()) + (m==null||m.trim().isEmpty()?"":" "+m.trim()) + (l==null||l.trim().isEmpty()?"":" "+l.trim())).trim();
                            fullnameMap.put(sid, fullname);
                        }
                    }
                } catch (ClassNotFoundException | java.sql.SQLException dbex) {
                    dbex.printStackTrace();
                    return "Database error: " + dbex.getMessage();
                }

                if (ids.isEmpty()) {
                    return "No students found for the given input/category (restricted to your classes).";
                }

                // 2) Prepare output directory
                java.io.File outDir = new java.io.File(outFolderPath);
                if (!outDir.exists() && !outDir.mkdirs()) {
                    return "Failed to create output folder: " + outFolderPath;
                }

                // 3) Generate & save, with batch duplicate handling
                int generated = 0, skipped = 0, replaced = 0;
                java.util.List<String> failedIds = new java.util.ArrayList<>();

                for (String sid : ids) {
                    if (isCancelled()) break;

                    java.io.File outFile = new java.io.File(outDir, sid + ".png");
                    if (outFile.exists()) {
                        if (dupAction == DuplicateAction.UNDECIDED) {
                            final int[] choice = new int[1];
                            try {
                                javax.swing.SwingUtilities.invokeAndWait(() -> {
                                    Object[] opts = {"Replace All", "Skip All", "Cancel"};
                                    int sel = javax.swing.JOptionPane.showOptionDialog(
                                            HomePage.this,
                                            "Some QR files already exist. Choose how to handle duplicates for the entire batch:",
                                            "Duplicate files detected",
                                            javax.swing.JOptionPane.DEFAULT_OPTION,
                                            javax.swing.JOptionPane.QUESTION_MESSAGE,
                                            null, opts, opts[0]);
                                    // 0 -> Replace All, 1 -> Skip All, 2 -> Cancel or closed(-1)
                                    choice[0] = sel;
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                choice[0] = 2;
                            }

                            if (choice[0] == 0) dupAction = DuplicateAction.REPLACE_ALL;
                            else if (choice[0] == 1) dupAction = DuplicateAction.SKIP_ALL;
                            else dupAction = DuplicateAction.CANCEL;
                        }

                        if (dupAction == DuplicateAction.SKIP_ALL) {
                            skipped++;
                            continue;
                        } else if (dupAction == DuplicateAction.CANCEL) {
                            break;
                        } // else REPLACE_ALL -> fall through to overwrite
                    }

                    try {
                        java.awt.image.BufferedImage qr = QRCodeGenerator.createQRCodeImage(sid, 300, 300);
                        QRCodeGenerator.saveQRCodeImage(qr, outFile);
                        generated++;
                        if (outFile.exists() && dupAction == DuplicateAction.REPLACE_ALL) replaced++;
                    } catch (com.google.zxing.WriterException we) {
                        we.printStackTrace();
                        failedIds.add(sid + " (create failed)");
                    } catch (java.io.IOException ioe) {
                        ioe.printStackTrace();
                        failedIds.add(sid + " (save failed)");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        failedIds.add(sid + " (error)");
                    }
                }

                // 4) Build summary
                StringBuilder sb = new StringBuilder();
                if (dupAction == DuplicateAction.CANCEL) sb.append("Operation cancelled by user.\n");
                sb.append("Total candidates: ").append(ids.size()).append("\n");
                sb.append("Generated: ").append(generated).append("\n");
                sb.append("Skipped: ").append(skipped).append("\n");
                sb.append("Failed: ").append(failedIds.size()).append("\n");
                if (!failedIds.isEmpty()) {
                    sb.append("Failed IDs: ").append(String.join(", ", failedIds)).append("\n");
                }
                return sb.toString();
            }

            @Override
            protected void done() {
                try {
                    String msg = get();
                    int type = javax.swing.JOptionPane.INFORMATION_MESSAGE;
                    if (msg == null || msg.startsWith("Database error") || msg.startsWith("Failed to")) type = javax.swing.JOptionPane.ERROR_MESSAGE;
                    else if (msg.contains("cancel") || msg.contains("Skipped")) type = javax.swing.JOptionPane.WARNING_MESSAGE;

                    Object[] options = {"Open folder", "Close"};
                    int sel = javax.swing.JOptionPane.showOptionDialog(
                            HomePage.this,
                            msg == null ? "Unknown result" : msg,
                            "Generate QR",
                            javax.swing.JOptionPane.DEFAULT_OPTION,
                            type,
                            null,
                            options,
                            options[1]);

                    if (sel == 0) {
                        // Open output folder in file explorer
                        try {
                            java.awt.Desktop.getDesktop().open(new java.io.File(outFolderPath));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            javax.swing.JOptionPane.showMessageDialog(HomePage.this, "Failed to open folder: " + ex.getMessage(), "Open Folder Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(HomePage.this, "Unexpected error: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();

    }//GEN-LAST:event_btnGenerateQRActionPerformed

    private void tfStudIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfStudIDKeyReleased
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            Toolkit.getDefaultToolkit().beep();   
            //System.out.println("ENTER pressed");
            btnGenerateQR.doClick(500);
        }
    }//GEN-LAST:event_tfStudIDKeyReleased

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
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                new HomePage().setVisible(true);
            }
        });
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
        String info = LName.toUpperCase() + ", " + FName + " " + "| Role: " + Role;
        if ("Instructor".equalsIgnoreCase(Role)) {
            info += " | ID: " + InstID;
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

            jPanel3.setOpaque(false);
            bgPanel.add(jPanel3, java.awt.BorderLayout.CENTER);

            setContentPane(bgPanel);
            pack(); // resize window correctly
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
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
    
        public String getLoggedInInstID() {
        return this.InstID;
    }
    
    public String getInstructorID() {
        return InstID;
    }
    
    public Connection getConnection() {
        return conn;
    }
    
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public void setInstructorID(String instID) {
        this.InstID = instID;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private customElements.NewButton BAttendanceLog;
    private customElements.NewButton BViewClass;
    private javax.swing.JLabel DisplayInfo;
    private customElements.NewButton IDScanner;
    private customElements.NewButton Logout;
    private customElements.NewButton btnGenerateQR;
    private customElements.NewButton btnViewProfile;
    private customElements.RoundedComboBox cbCategory;
    private javax.swing.JLabel generateQR;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblGenerateQR;
    private customElements.RoundedComboBox roundedComboBox1;
    private customElements.NewTextField tfStudID;
    // End of variables declaration//GEN-END:variables
}
