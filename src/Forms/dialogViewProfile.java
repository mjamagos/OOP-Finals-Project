package Forms;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import java.sql.Connection;


public class dialogViewProfile extends javax.swing.JDialog {

    private String InstID;
    private String Role;
    private String FName;
    private String MName;
    private String LName;

    private static final String UNIVERSITY_NAME = "University of Northern Philippines";

    public dialogViewProfile(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        // show default university immediately
        phUniversity.setText(UNIVERSITY_NAME);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        panel21 = new customElements.Panel2();
        phName = new javax.swing.JLabel();
        phRole = new javax.swing.JLabel();
        panel22 = new customElements.Panel2();
        lblDetails = new javax.swing.JLabel();
        lblDepartment = new javax.swing.JLabel();
        lblCollege = new javax.swing.JLabel();
        lblUniversity = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        changeCred = new customElements.NewButton();
        phCollege = new javax.swing.JLabel();
        phUniversity = new javax.swing.JLabel();
        phDepartment = new javax.swing.JLabel();
        phAddress = new javax.swing.JLabel();
        phEmail = new javax.swing.JLabel();
        Back = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(40, 17, 84));

        lblTitle.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblTitle.setText("Profile");

        panel21.setBackground(new java.awt.Color(40, 17, 84));

        phName.setBackground(new java.awt.Color(255, 255, 255));
        phName.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        phName.setForeground(new java.awt.Color(255, 255, 255));
        phName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        phName.setText("Name");

        phRole.setBackground(new java.awt.Color(255, 255, 0));
        phRole.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        phRole.setForeground(new java.awt.Color(255, 255, 255));
        phRole.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        phRole.setText("Role");

        javax.swing.GroupLayout panel21Layout = new javax.swing.GroupLayout(panel21);
        panel21.setLayout(panel21Layout);
        panel21Layout.setHorizontalGroup(
            panel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(phName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(phRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panel21Layout.setVerticalGroup(
            panel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel21Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(phName, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phRole)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        panel22.setBackground(new java.awt.Color(40, 17, 84));

        lblDetails.setBackground(new java.awt.Color(255, 255, 255));
        lblDetails.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        lblDetails.setForeground(new java.awt.Color(255, 255, 255));
        lblDetails.setText("Details");

        lblDepartment.setBackground(new java.awt.Color(255, 255, 255));
        lblDepartment.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblDepartment.setForeground(new java.awt.Color(255, 255, 255));
        lblDepartment.setText("Department");

        lblCollege.setBackground(new java.awt.Color(255, 255, 255));
        lblCollege.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblCollege.setForeground(new java.awt.Color(255, 255, 255));
        lblCollege.setText("College");

        lblUniversity.setBackground(new java.awt.Color(255, 255, 255));
        lblUniversity.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblUniversity.setForeground(new java.awt.Color(255, 255, 255));
        lblUniversity.setText("University");

        lblEmail.setBackground(new java.awt.Color(255, 255, 255));
        lblEmail.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblEmail.setText("Email");

        lblAddress.setBackground(new java.awt.Color(255, 255, 255));
        lblAddress.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        lblAddress.setForeground(new java.awt.Color(255, 255, 255));
        lblAddress.setText("Address");

        changeCred.setText("Change Login Credentials");
        changeCred.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        changeCred.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeCredActionPerformed(evt);
            }
        });

        phCollege.setBackground(new java.awt.Color(255, 255, 255));
        phCollege.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        phCollege.setForeground(new java.awt.Color(255, 255, 255));
        phCollege.setText("College");

        phUniversity.setBackground(new java.awt.Color(255, 255, 255));
        phUniversity.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        phUniversity.setForeground(new java.awt.Color(255, 255, 255));
        phUniversity.setText("University");

        phDepartment.setBackground(new java.awt.Color(255, 255, 255));
        phDepartment.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        phDepartment.setForeground(new java.awt.Color(255, 255, 255));
        phDepartment.setText("Department");

        phAddress.setBackground(new java.awt.Color(255, 255, 255));
        phAddress.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        phAddress.setForeground(new java.awt.Color(255, 255, 255));
        phAddress.setText("Address");

        phEmail.setBackground(new java.awt.Color(255, 255, 255));
        phEmail.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        phEmail.setForeground(new java.awt.Color(255, 255, 255));
        phEmail.setText("Email");

        javax.swing.GroupLayout panel22Layout = new javax.swing.GroupLayout(panel22);
        panel22.setLayout(panel22Layout);
        panel22Layout.setHorizontalGroup(
            panel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel22Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblDetails)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel22Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(panel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel22Layout.createSequentialGroup()
                        .addGroup(panel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUniversity)
                            .addComponent(lblDepartment)
                            .addComponent(lblCollege, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(panel22Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(panel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel22Layout.createSequentialGroup()
                                .addComponent(phDepartment)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panel22Layout.createSequentialGroup()
                                .addGroup(panel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(phCollege)
                                    .addComponent(phUniversity))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblAddress)
                                    .addComponent(lblEmail)
                                    .addGroup(panel22Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(panel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(phAddress)
                                            .addComponent(phEmail))))
                                .addGap(121, 121, 121))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(changeCred, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        panel22Layout.setVerticalGroup(
            panel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel22Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(lblDetails)
                .addGap(18, 18, 18)
                .addGroup(panel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel22Layout.createSequentialGroup()
                        .addComponent(lblUniversity)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(phUniversity)
                        .addGap(18, 18, 18)
                        .addComponent(lblCollege)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(phCollege)
                        .addGap(18, 18, 18)
                        .addComponent(lblDepartment))
                    .addGroup(panel22Layout.createSequentialGroup()
                        .addComponent(lblAddress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(phAddress)
                        .addGap(18, 18, 18)
                        .addComponent(lblEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(phEmail)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phDepartment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(changeCred, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        Back.setFont(new java.awt.Font("Poppins Medium", 0, 14)); // NOI18N
        Back.setForeground(new java.awt.Color(255, 255, 255));
        Back.setText("< Back");
        Back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Back)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Back)
                    .addComponent(lblTitle))
                .addGap(22, 22, 22)
                .addComponent(panel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void BackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackMouseClicked
        setVisible(false);
        dispose();
    }//GEN-LAST:event_BackMouseClicked

    private void changeCredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeCredActionPerformed
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        if (owner == null) owner = new Frame();

        dialogChangePass dlg = new dialogChangePass(owner, true);
        dlg.setLoggedInUser(this.InstID, this.Role, this.FName, this.MName, this.LName);
        dlg.loadCurrentCredentials(this.InstID);

        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }//GEN-LAST:event_changeCredActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dialogViewProfile dialog = new dialogViewProfile(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public void setLoggedInUser(String InstID, String Role, String FName, String MName, String LName) {
        this.InstID = InstID;
        this.Role = Role;
        this.FName = FName;
        this.MName = MName;
        this.LName = LName;
        displayName();
        loadInstructorDetailsFromDB();
    }
    
    // Compose and display the name/role portion (very small helper)
    private void displayName() {
        StringBuilder sb = new StringBuilder();
        if (FName != null && !FName.trim().isEmpty()) sb.append(FName.trim());
        if (MName != null && !MName.trim().isEmpty()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(MName.trim());
        }
        if (LName != null && !LName.trim().isEmpty()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(LName.trim());
        }
        String full = sb.length() == 0 ? "Name" : sb.toString();

        // update UI on EDT
        SwingUtilities.invokeLater(() -> {
            phName.setText(full);
            phRole.setText(Role == null ? "" : Role);
            phUniversity.setText(UNIVERSITY_NAME);
        });
    }

    private void loadInstructorDetailsFromDB() {
        // clear if no InstID
        if (InstID == null || InstID.trim().isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                phEmail.setText("");
                phAddress.setText("");
                phDepartment.setText("");
                phCollege.setText("");
                phUniversity.setText(UNIVERSITY_NAME);
            });
            return;
        }

        new Thread(() -> {
            InstructorInfo info = null;
            try (Connection c = DBConfig.getConnection()) {
                // Use repo method (keeps dialog free of SQL)
                StudentRepo repo = new StudentRepo(c);
                info = repo.fetchInstructorByID(InstID);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            final InstructorInfo fi = info;
            SwingUtilities.invokeLater(() -> {
                String fname = (FName==null?"":FName);
                String mname = (MName==null?"":(" " + MName)).trim();
                String lname = (LName==null?"":(" " + LName)).trim();
                String fullName = (fname + (mname.isEmpty() ? "" : " " + mname) + (lname.isEmpty() ? "" : " " + lname)).trim();

                if (fi == null) {
                    // no data: clear fields
                    phEmail.setText("");
                    phAddress.setText("");
                    phDepartment.setText("");
                    phCollege.setText("");
                    phUniversity.setText(UNIVERSITY_NAME);
                } else {
                    // apply values directly (no wrapping)
                    phEmail.setText(fi.email == null ? "" : fi.email);
                    phAddress.setText(fi.address == null ? "" : fi.address);
                    phDepartment.setText(fi.department == null ? "" : fi.department);
                    phCollege.setText(fi.college == null ? "" : fi.college);
                    phUniversity.setText(fi.university == null || fi.university.isEmpty() ? UNIVERSITY_NAME : fi.university);

                    // After updating long values, ask layout to recompute and resize to preferred size
                    // This keeps the dialog "just big enough" for the new content.
                    panel22.revalidate();
                    panel22.repaint();
                    pack();
                    setLocationRelativeTo(getParent());
                }
            });
        }, "Profile-Loader").start();
    }

    private String fullNameFromInfo(InstructorInfo fi) {
        StringBuilder sb = new StringBuilder();
        if (fi.fname != null && !fi.fname.isEmpty()) sb.append(fi.fname);
        if (fi.mname != null && !fi.mname.isEmpty()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(fi.mname);
        }
        if (fi.lname != null && !fi.lname.isEmpty()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(fi.lname);
        }
        return sb.toString();
    }

    private void setCenter() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int left = (d.width - this.getWidth()) / 2;
        int top = (d.height - this.getHeight()) / 2;
        this.setLocation(left, top);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Back;
    private customElements.NewButton changeCred;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblCollege;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblDetails;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUniversity;
    private customElements.Panel2 panel21;
    private customElements.Panel2 panel22;
    private javax.swing.JLabel phAddress;
    private javax.swing.JLabel phCollege;
    private javax.swing.JLabel phDepartment;
    private javax.swing.JLabel phEmail;
    private javax.swing.JLabel phName;
    private javax.swing.JLabel phRole;
    private javax.swing.JLabel phUniversity;
    // End of variables declaration//GEN-END:variables
}
