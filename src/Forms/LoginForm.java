/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;


/**
 * LoginForm.java
 * @author Jobelle
 * ----------------------------
 * A GUI login form for the Class Attendance System.
 * Authenticates users (Student or Instructor) against the MySQL database
 * and redirects to their respective main pages upon successful login.
 */
public class LoginForm extends javax.swing.JFrame {
    
    public LoginForm() {
        initComponents();
        setCenter(); // Centers the window on screen
        useCustomBackground(); // Sets custom background
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panel1 = new customElements.Panel();
        labelLogin = new javax.swing.JLabel();
        RegisterLink = new javax.swing.JLabel();
        BLogin = new customElements.NewButton();
        jPanel2 = new javax.swing.JPanel();
        labelUSerId = new javax.swing.JLabel();
        UserIDField = new customElements.TextField();
        labelPassword = new javax.swing.JLabel();
        PasswordField = new customElements.PassField();
        CBShowPass = new customElements.CheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel1.setBackground(new java.awt.Color(40, 17, 84));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 700));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        labelLogin.setBackground(new java.awt.Color(255, 206, 99));
        labelLogin.setFont(new java.awt.Font("Poppins SemiBold", 0, 30)); // NOI18N
        labelLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelLogin.setText("LOGIN");

        RegisterLink.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        RegisterLink.setText("Not registered yet? Sign up.");

        BLogin.setText("Login");
        BLogin.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        BLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BLoginActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        labelUSerId.setBackground(new java.awt.Color(94, 106, 111));
        labelUSerId.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        labelUSerId.setText("Username");

        UserIDField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                UserIDFieldKeyReleased(evt);
            }
        });

        labelPassword.setBackground(new java.awt.Color(94, 106, 111));
        labelPassword.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        labelPassword.setText("Password");

        PasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                PasswordFieldKeyReleased(evt);
            }
        });

        CBShowPass.setText("Show Password");
        CBShowPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBShowPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CBShowPass, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelPassword)
                            .addComponent(labelUSerId))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(UserIDField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(184, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelUSerId)
                    .addComponent(UserIDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CBShowPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(262, 262, 262)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(BLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(RegisterLink))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(labelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(62, 62, 62)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(49, 49, 49)
                .addComponent(BLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RegisterLink, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(153, 153, 153))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(139, 139, 139))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Triggers login when ENTER is pressed in the User ID field
    private void UserIDFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UserIDFieldKeyReleased
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            Toolkit.getDefaultToolkit().beep();   
            BLogin.doClick(500);
        }
    }//GEN-LAST:event_UserIDFieldKeyReleased
    // Trigger login when ENTER is pressed in the Password field
    private void PasswordFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordFieldKeyReleased
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            Toolkit.getDefaultToolkit().beep();   
            //System.out.println("ENTER pressed");
            BLogin.doClick(500);
        }
    }//GEN-LAST:event_PasswordFieldKeyReleased

    /**
     * Handles the login button action.
     * Verifies user credentials against the database using PreparedStatement
     * to prevent SQL injection. Redirects the user to the appropriate main page
     * (student or instructor) if login is successful.
     */
    private void BLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BLoginActionPerformed
        try (Connection conn = DBConfig.getConnection()) {

            String sql = "SELECT * FROM tblaccount WHERE Username = ? AND Password = ?";
            try (PreparedStatement pstatement = conn.prepareStatement(sql)) {
                pstatement.setString(1, UserIDField.getText());
                pstatement.setString(2, PasswordField.getText());

                try (ResultSet rs = pstatement.executeQuery()) {
                    int i = 0;
                    String Role = "";
                    String StudID = "";
                    String InstID = "";
                    String Username = "";

                    while (rs.next()) {
                        i++;
                        Role = rs.getString("Role"); //student or instructor
                        StudID = rs.getString("StudID");
                        InstID = rs.getString("InstID");
                        Username = rs.getString("Username");
                    }

                    String FName = "";
                    String MName = "";
                    String LName = "";

                    if (i > 0) { // valid account
                        if ("Instructor".equalsIgnoreCase(Role)) {
                            // Use PreparedStatement to fetch instructor details
                            try (PreparedStatement pst = conn.prepareStatement("SELECT FName, MName, LName FROM tblinstructor WHERE InstID = ?")) {
                                pst.setString(1, InstID);
                                try (ResultSet rs2 = pst.executeQuery()) {
                                    if (rs2.next()) {
                                        FName = rs2.getString("FName");
                                        MName = rs2.getString("MName");
                                        LName = rs2.getString("LName");
                                    }
                                }
                            }

                            HomePage f1 = new HomePage();
                            f1.setVisible(true);
                            f1.setLoggedInUser(InstID, Role, FName, MName, LName);
                        }

                        this.dispose();
                    } else { // invalid account
                        JOptionPane.showMessageDialog(null, "Account does not exist. Please enter valid credentials.");
                        UserIDField.setText("");
                        PasswordField.setText("");
                        UserIDField.requestFocus();
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Can't load driver " + e);
        } catch (SQLException e) {
            System.out.println("Database access failed " + e);
        }
    }//GEN-LAST:event_BLoginActionPerformed

    private void CBShowPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBShowPassActionPerformed
        if (CBShowPass.isSelected()) {
            PasswordField.setEchoChar((char) 0);
        } else {
            PasswordField.setEchoChar('•');
        }
    }//GEN-LAST:event_CBShowPassActionPerformed

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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
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
                getClass().getResource("/images/QRrive.jpg")
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
    
    public class DBConnection{
        public static Connection getConnection() throws SQLException, ClassNotFoundException{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/classattendance", "root", ""
            );
        } 
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private customElements.NewButton BLogin;
    private customElements.CheckBox CBShowPass;
    private customElements.PassField PasswordField;
    private javax.swing.JLabel RegisterLink;
    private customElements.TextField UserIDField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JLabel labelUSerId;
    private customElements.Panel panel1;
    // End of variables declaration//GEN-END:variables
}
