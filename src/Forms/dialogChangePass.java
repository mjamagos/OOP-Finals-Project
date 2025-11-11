
package Forms;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

/**
 * dialogChangePass — change username/password for instructor accounts.
 *
 * Constructor and signature preserved exactly as in your original file.
 * Checkbox + button wiring moved into generated event handlers so constructor is untouched.
 *
 * IMPORTANT: If you store hashed passwords, replace the direct password write
 * with your hash function before saving.
 */
public class dialogChangePass extends java.awt.Dialog {

    private StudentRepo dao;        // <- kept in case needed
    private String studentID;      // selected student ID (StudID)

    //private HomePage parentFrame;
    private String InstID;
    private String Role;
    private String FName;
    private String MName;
    private String LName;

    /**
     * Creates new form dialogChangePass
     *
     * Constructor preserved exactly like your original.
     */
    public dialogChangePass(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setCenter();
    }

    /**
     * Load current username for the given InstID (if any) and prefill the username field.
     * Runs a quick DB query and fills tfUsername (non-blocking via SwingUtilities.invokeLater).
     */
    public void loadCurrentCredentials(String instID) {
        if (instID == null || instID.trim().isEmpty()) return;
        this.InstID = instID;

        SwingUtilities.invokeLater(() -> {
            try (Connection c = DBConfig.getConnection()) {
                try (PreparedStatement pst = c.prepareStatement("SELECT Username FROM tblaccount WHERE InstID = ? LIMIT 1")) {
                    pst.setString(1, instID);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            String username = rs.getString("Username");
                            tfUsername.setText(username == null ? "" : username);
                        } else {
                            tfUsername.setText("");
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                // leave username blank on error
            }
        });
    }

    /**
     * Save changes: validate and update tblaccount for the current InstID.
     * If no row exists, INSERT one. Returns true on success.
     */
    public boolean saveChanges() {
        String username = tfUsername.getText() == null ? "" : tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword() == null ? new char[0] : pfPassword.getPassword()).trim();

        if (InstID == null || InstID.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No instructor selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username.", "Validation", JOptionPane.WARNING_MESSAGE);
            tfUsername.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a password.", "Validation", JOptionPane.WARNING_MESSAGE);
            pfPassword.requestFocus();
            return false;
        }

        try (Connection c = DBConfig.getConnection()) {
            // check if account exists
            boolean exists = false;
            try (PreparedStatement pc = c.prepareStatement("SELECT 1 FROM tblaccount WHERE InstID = ? LIMIT 1")) {
                pc.setString(1, InstID);
                try (ResultSet rc = pc.executeQuery()) {
                    exists = rc.next();
                }
            }

            if (exists) {
                try (PreparedStatement pu = c.prepareStatement("UPDATE tblaccount SET Username = ?, Password = ? WHERE InstID = ?")) {
                    pu.setString(1, username);
                    pu.setString(2, password); // replace with hash(...) if needed
                    pu.setString(3, InstID);
                    int affected = pu.executeUpdate();
                    if (affected <= 0) throw new Exception("No rows updated");
                }
            } else {
                try (PreparedStatement pi = c.prepareStatement("INSERT INTO tblaccount (InstID, Username, Password) VALUES (?, ?, ?)")) {
                    pi.setString(1, InstID);
                    pi.setString(2, username);
                    pi.setString(3, password); // replace with hash(...) if needed
                    pi.executeUpdate();
                }
            }

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update credentials: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * setLoggedInUser — keep copy of the current user context if caller wants it.
     */
    public void setLoggedInUser(String InstID, String Role, String FName, String MName, String LName) {
        SwingUtilities.invokeLater(() -> {
            this.InstID = InstID;
            this.Role = Role;
            this.FName = FName;
            this.MName = MName;
            this.LName = LName;
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnSave = new customElements.NewButton();
        btnCancel = new customElements.NewButton();
        panel21 = new customElements.Panel2();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        tfUsername = new customElements.TextField();
        pfPassword = new customElements.PassField();
        checkPass = new customElements.CheckBox();
        lblChangePass = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        btnSave.setText("Save Changes");
        btnSave.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 51, 51));
        btnCancel.setText("Cancel");
        btnCancel.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        panel21.setBackground(new java.awt.Color(255, 255, 255));

        lblUsername.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblUsername.setText("Username");

        lblPassword.setFont(new java.awt.Font("Poppins Medium", 0, 12)); // NOI18N
        lblPassword.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblPassword.setText("Password");
        lblPassword.setToolTipText("");

        tfUsername.setText("textField1");

        checkPass.setText("Show Password");
        checkPass.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N
        checkPass.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkPassItemStateChanged(evt);
            }
        });
        checkPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel21Layout = new javax.swing.GroupLayout(panel21);
        panel21.setLayout(panel21Layout);
        panel21Layout.setHorizontalGroup(
            panel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel21Layout.createSequentialGroup()
                .addGroup(panel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel21Layout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addComponent(pfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblUsername)
                        .addComponent(lblPassword))
                    .addGroup(panel21Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(panel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        panel21Layout.setVerticalGroup(
            panel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel21Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(checkPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        lblChangePass.setFont(new java.awt.Font("Poppins Medium", 0, 18)); // NOI18N
        lblChangePass.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChangePass.setText("Change Login Credential");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
            .addComponent(lblChangePass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblChangePass)
                .addGap(12, 12, 12)
                .addComponent(panel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void checkPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPassActionPerformed
        if (checkPass.isSelected()) {
            pfPassword.setEchoChar((char) 0);
        } else {
            pfPassword.setEchoChar('•');
        }
    }//GEN-LAST:event_checkPassActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        boolean ok = saveChanges();
        if (ok) {
            JOptionPane.showMessageDialog(this, "Credentials updated successfully.", "Saved", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void checkPassItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkPassItemStateChanged

    }//GEN-LAST:event_checkPassItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dialogChangePass dialog = new dialogChangePass(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    private void setCenter() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int left = (d.width - this.getWidth()) / 2;
        int top = (d.height - this.getHeight()) / 2;
        this.setLocation(left, top);
    }
    
    // getters (used by launcher if needed)
    public customElements.NewButton getBtnSave() { return btnSave; }
    public customElements.NewButton getBtnCancel() { return btnCancel; }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private customElements.NewButton btnCancel;
    private customElements.NewButton btnSave;
    private customElements.CheckBox checkPass;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblChangePass;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private customElements.Panel2 panel21;
    private customElements.PassField pfPassword;
    private customElements.TextField tfUsername;
    // End of variables declaration//GEN-END:variables
}
