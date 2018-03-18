/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import enums.StaffEnum;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.table.DefaultTableModel;
import properties.StaffPro;

/**
 *
 * @author Huda
 */
public class pnlUsers extends javax.swing.JPanel {

    /**
     * Creates new form pnlUsers
     */
    public pnlUsers() {
        initComponents();
        personellerGetir();
        backGroundColor();
    }
    public void backGroundColor(){
    this.setBackground(new Color(119, 119, 119));
    jPanel1.setBackground(new Color(119, 119, 119));
    jPanel2.setBackground(new Color(119, 119, 119));
    personelTable.setBackground(new Color(160, 160, 160));
    btnKAta.setBackground(new Color(75, 75, 75));
   
}
    
    
    Db db = new Db("oteldenemeleri", "root", "");
    LoggerClass lg = new LoggerClass();
    ArrayList<StaffPro> sp = new ArrayList<>();
    int staff_id=-1;
    String authory="0";

    private void personellerGetir() {
        try {
            ResultSet rs = db.connect().executeQuery("call staffSelectNotUser();");
            while (rs.next()) {
                StaffPro s = new StaffPro();
                s.setStaff_id(rs.getInt(""+StaffEnum.staff_id));
                s.setFirst_name(rs.getString(""+StaffEnum.first_name));
                s.setLast_name(rs.getString(""+StaffEnum.last_name));
                s.setIdentity_number(rs.getString(""+StaffEnum.identity_number));
                s.setPhone_number(rs.getString(""+StaffEnum.phone_number));
                s.setStaff_title(rs.getString(""+StaffEnum.staff_title));
                s.setStart_date(rs.getString(""+StaffEnum.start_date));
                s.setQuit_date(rs.getString(""+StaffEnum.quit_date));
                s.setSalary(rs.getInt(""+StaffEnum.salary));
                s.setTown_id(rs.getString(""+StaffEnum.town_id));
                s.setAddress(rs.getString(""+StaffEnum.address));
                s.setStaff_state(rs.getString(""+StaffEnum.staff_state));
                sp.add(s);
            }
            PersonelDoldur();

        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("personel getirme hatasi " + ex);
        }
    }
   

    private void PersonelDoldur() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Adı");
        dtm.addColumn("Soyadı");
        dtm.addColumn("Unvanı");
        dtm.addColumn("Başlama Tarihi");

        for (StaffPro item : sp) {
            String Tarih = item.getStart_date();
            String[] dizi = {item.getFirst_name(), item.getLast_name(), item.getStaff_title(), Tarih};
            dtm.addRow(dizi);
        }
        personelTable.setModel(dtm);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        personelTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        ytCmb = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnKAta = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        unameTxt = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        passwordTxt = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(600, 600));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Personel Yetkilendime"));

        personelTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        personelTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                personelTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(personelTable);

        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Personel Seçimi");

        ytCmb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Resepsiyonist", "Yönetici" }));
        ytCmb.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ytCmbİtemStateChanged(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Yetki Türü");

        btnKAta.setText("Kullanıcı Olarak Ata");
        btnKAta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKAtaActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Yeni Kullanıcı Bilgileri"));

        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Atanan Kullanıcı Adı ");

        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Atanan Parola");

        passwordTxt.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(unameTxt)
                    .addComponent(passwordTxt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(unameTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passwordTxt))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ytCmb, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKAta)
                .addContainerGap(231, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKAta)
                    .addComponent(ytCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(26, 26, 26)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void personelTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_personelTableMouseClicked
        staff_id=sp.get(personelTable.getSelectedRow()).getStaff_id();
    }//GEN-LAST:event_personelTableMouseClicked

    private void ytCmbİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ytCmbİtemStateChanged
        authory=String.valueOf(ytCmb.getSelectedIndex());
    }//GEN-LAST:event_ytCmbİtemStateChanged

    private void btnKAtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKAtaActionPerformed
        try {
            Random rnd=new Random();
            String username=sp.get(personelTable.getSelectedRow()).getFirst_name()+rnd.nextInt(1000);
            char[] cr={'a','e','i','u','ü','o','ö','g'};
            String userpassword=""+rnd.nextInt(10)+cr[rnd.nextInt(cr.length)]+rnd.nextInt(10)+cr[rnd.nextInt(cr.length)]+rnd.nextInt(10)+cr[rnd.nextInt(cr.length)];
            PreparedStatement pr=db.preConnect("call userInsertPro(?,?,?,?);");
            pr.setString(1, username);
            pr.setString(2, userpassword);
            pr.setString(3, authory);
            pr.setInt(4, staff_id);
            pr.executeUpdate();
            unameTxt.setText(username);
            passwordTxt.setText(userpassword);
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("yeni kullanıcı atama hatası : "+ex);
        }
    }//GEN-LAST:event_btnKAtaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKAta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel passwordTxt;
    private javax.swing.JTable personelTable;
    private javax.swing.JLabel unameTxt;
    private javax.swing.JComboBox<String> ytCmb;
    // End of variables declaration//GEN-END:variables
}