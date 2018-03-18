package application;

import enums.userEnum;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import properties.UserPro;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
        jPanel1.setBackground(new Color(46, 49, 55));
        jPanel2.setBackground(new Color(84, 93, 106));
        jPanel3.setBackground(new Color(84, 93, 106));
        getContentPane().setBackground(new Color(46, 49, 55));
        btnLogin.setBackground(new Color(144, 186, 38));
        txtUserName.setBackground(new Color(84, 93, 106));
        pswUserPassword.setBackground(new Color(84, 93, 106));
        txtUserName.setBorder(null);

        txtUserName.setBorder(null);
        pswUserPassword.setBorder(null);
        btnLogin.setBorder(null);
        txtUserName.requestFocus();

//        ImageIcon ii = new ImageIcon("palm.png");
//        this.setIconImage(new ImageIcon("palm.png").getImage());

    }

    Db db = new Db("otelDenemeleri", "root", "");
    LoggerClass lg = new LoggerClass();

    public void giris() {
        String kAdi = txtUserName.getText();
        String sifre = pswUserPassword.getText();
        try {
            String q = "Call loginSelectPro(?,?)";
            PreparedStatement prs = db.preConnect(q);
            prs.setString(1, kAdi);
            prs.setString(2, sifre);
            ResultSet rs = prs.executeQuery();
            if (rs.next()) {
                UserPro up = new UserPro();
                up.setUser_name(rs.getString(userEnum.user_name + ""));
                up.setAuthory(rs.getString(userEnum.authory + ""));
                up.setPassword(rs.getString(userEnum.password + ""));
                up.setStaff_id(rs.getString(userEnum.staff_id + ""));
                HotelOtomation hm = new HotelOtomation(up);
                hm.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Hatalı kullanıcı adı veya şifre girdiniz!");
                txtUserName.setText("");
                pswUserPassword.setText("");
                txtUserName.requestFocus();
            }
        } catch (HeadlessException | SQLException e) {
            lg.loggerFunction(e);
//            System.err.println("Data Getirme Hatası: " + e);
        } finally {
            db.disconnect();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnLogo = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pswUserPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kullanıcı Girişi");
        setIconImage(new ImageIcon("palm.png").getImage());
        setLocation(new java.awt.Point(500, 225));
        setResizable(false);

        btnLogo.setBackground(new java.awt.Color(255, 255, 204));
        btnLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logoSon.png"))); // NOI18N
        btnLogo.setPreferredSize(new java.awt.Dimension(50, 50));
        btnLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoMouseExited(evt);
            }
        });

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Giriş");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/if_jee-74_2180663.png"))); // NOI18N

        txtUserName.setForeground(new java.awt.Color(204, 204, 204));
        txtUserName.setText("Kullanıcı Adı");
        txtUserName.setOpaque(false);
        txtUserName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUserNameMouseClicked(evt);
            }
        });
        txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUserNameKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUserName, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );

        jPanel3.setLayout(null);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/if_Lock_1214985.png"))); // NOI18N
        jPanel3.add(jLabel4);
        jLabel4.setBounds(0, 10, 20, 20);

        pswUserPassword.setForeground(new java.awt.Color(204, 204, 204));
        pswUserPassword.setText("Şifre");
        pswUserPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pswUserPasswordMouseClicked(evt);
            }
        });
        pswUserPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pswUserPasswordKeyTyped(evt);
            }
        });
        jPanel3.add(pswUserPassword);
        pswUserPassword.setBounds(30, 10, 160, 20);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUserNameMouseClicked

        txtUserName.setText("");
        txtUserName.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtUserNameMouseClicked
    int i = 0;
    private void pswUserPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pswUserPasswordMouseClicked

        pswUserPassword.setText("");
        pswUserPassword.setForeground(Color.BLACK);
    }//GEN-LAST:event_pswUserPasswordMouseClicked

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        giris();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtUserNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserNameKeyTyped

        if (i == 0) {
            txtUserName.setText("");
            txtUserName.setForeground(Color.BLACK);
            i = 1;
        }
    }//GEN-LAST:event_txtUserNameKeyTyped

    private void pswUserPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pswUserPasswordKeyTyped

        if (i == 0) {
            pswUserPassword.setText("");
            pswUserPassword.setForeground(Color.BLACK);
            i = 1;
        }
    }//GEN-LAST:event_pswUserPasswordKeyTyped

    private void btnLogoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoMouseEntered

        btnLogo.setBackground(new Color(255, 255, 124));
    }//GEN-LAST:event_btnLogoMouseEntered

    private void btnLogoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoMouseExited
        btnLogo.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_btnLogoMouseExited

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLogo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField pswUserPassword;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
