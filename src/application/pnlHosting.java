/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import enums.CustomerEnum;
import enums.hostEnum;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Huda
 */
public class pnlHosting extends javax.swing.JPanel {

    /**
     * Creates new form pnlHosting
     */
    public pnlHosting() {
        initComponents();
        tableDoldur();
        backGroundColor();

    }

    Db db = new Db("oteldenemeleri", "root", "");
    LoggerClass lg = new LoggerClass();

    public void backGroundColor() {
        this.setBackground(new Color(119, 119, 119));
        jPanel1.setBackground(new Color(119, 119, 119));
        jPanel3.setBackground(new Color(119, 119, 119));
        txtAra.setBackground(new Color(160, 160, 160));
        btnAra.setBackground(new Color(75, 75, 75));
        jTable1.setBackground(new Color(160, 160, 160));

    }

    public void search() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Adı");
        dtm.addColumn("Soyadı");
        dtm.addColumn("Cep No");
        dtm.addColumn("Mail");
        dtm.addColumn("Giriş Tarihi");
        dtm.addColumn("Çıkış Tarihi");
        if ((txtAra.getText().trim().equals("") || txtAra.getText().equals("ara")) && dcStart.getDate() == null) {
            tableDoldur();
        } else if (!txtAra.getText().trim().equals("") && dcStart.getDate() == null) {
            String search = fullTextConvert(txtAra.getText().trim());

            try {
                PreparedStatement prs = db.preConnect("call hostingCustomerNameSearch(?)");
                prs.setString(1, search);
                ResultSet rs = prs.executeQuery();
                while (rs.next()) {
                    dtm.addRow(new String[]{rs.getString(CustomerEnum.customer_id + ""), rs.getString(CustomerEnum.first_name + ""), rs.getString(CustomerEnum.last_name + ""), rs.getString(CustomerEnum.phone_number + ""), rs.getString(CustomerEnum.email + ""), rs.getString(hostEnum.enter_date + ""), rs.getString(hostEnum.exit_date + "")});
                }
                jTable1.setModel(dtm);
            } catch (SQLException ex) {
                lg.loggerFunction(ex);
//                System.err.println("hostingCustomerNameSearch Data Getirme Hatasi: " + e);
            } finally {
                db.disconnect();
            }
        } else if ((txtAra.getText().trim().equals("") || txtAra.getText().equals("Ara")) && dcStart.getDate() != null) {

            if (dcEnd.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Lutfen lutfen ikinci tarih alanini doldurunuz!");
            } else {

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String dateStart = df.format(dcStart.getDate());
                String dateEnd = df.format(dcEnd.getDate());
//                System.out.println("tarihler");
                try {
                    ResultSet rs = db.connect().executeQuery("call hostingCustomerDateSearch('" + dateStart + "','" + dateEnd + "')");
                    while (rs.next()) {
                        dtm.addRow(new String[]{rs.getString(CustomerEnum.customer_id + ""), rs.getString(CustomerEnum.first_name + ""), rs.getString(CustomerEnum.last_name + ""), rs.getString(CustomerEnum.phone_number + ""), rs.getString(CustomerEnum.email + ""), rs.getString(hostEnum.enter_date + ""), rs.getString(hostEnum.exit_date + "")});
                    }
                    jTable1.setModel(dtm);
                } catch (SQLException ex) {
                    lg.loggerFunction(ex);
//                    System.err.println("hostingCustomerDateSearch Data Getirme Hatasi: " + e);
                } finally {
                    db.disconnect();
                }
            }
        } else {
            if (dcEnd.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Lutfen lutfen ikinci tarih alanini doldurunuz!");
            } else {
                String search = fullTextConvert(txtAra.getText().trim());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String dateStart = df.format(dcStart.getDate());
                String dateEnd = df.format(dcEnd.getDate());

                try {
                    ResultSet rs = db.connect().executeQuery("call hostingCustomerAllSearch('" + search + "','" + dateStart + "','" + dateEnd + "')");
                    while (rs.next()) {
                        dtm.addRow(new String[]{rs.getString(CustomerEnum.customer_id + ""), rs.getString(CustomerEnum.first_name + ""), rs.getString(CustomerEnum.last_name + ""), rs.getString(CustomerEnum.phone_number + ""), rs.getString(CustomerEnum.email + ""), rs.getString(hostEnum.enter_date + ""), rs.getString(hostEnum.exit_date + "")});
                    }
                    jTable1.setModel(dtm);
                } catch (SQLException ex) {
                    lg.loggerFunction(ex);
//                    System.err.println("hostingCustomerAllSearch Data Getirme Hatasi: " + e);
                } finally {
                    db.disconnect();
                }

            }
        }
    }

    public String fullTextConvert(String data) {
        String gonder = "";
        String[] dizi = data.split(" ");
        for (String item : dizi) {
            if (!item.trim().equals("")) {
                gonder += item + "*";
            }
        }
        return gonder;
    }

    public void tableDoldur() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Adı");
        dtm.addColumn("Soyadı");
        dtm.addColumn("Cep No");
        dtm.addColumn("Mail");
        dtm.addColumn("Giriş Tarihi");
        dtm.addColumn("Çıkış Tarihi");
        try {
            String q = "call hostingCustomerSelectPro();";
            ResultSet rs = db.connect().executeQuery(q);
            while (rs.next()) {
                dtm.addRow(new String[]{rs.getString(CustomerEnum.customer_id + ""), rs.getString(CustomerEnum.first_name + ""), rs.getString(CustomerEnum.last_name + ""), rs.getString(CustomerEnum.phone_number + ""), rs.getString(CustomerEnum.email + ""), rs.getString(hostEnum.enter_date + ""), rs.getString(hostEnum.exit_date + "")});
            }
            jTable1.setModel(dtm);
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("hostingCustomerSelectPro Data Getirme Hatasi: " + e);
        } finally {
            db.disconnect();
        }
    }

    int i = 0;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        txtAra = new javax.swing.JTextField();
        btnAra = new javax.swing.JButton();
        dcStart = new com.toedter.calendar.JDateChooser();
        dcEnd = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Arama"));
        jPanel3.setLayout(null);

        txtAra.setForeground(new java.awt.Color(51, 51, 51));
        txtAra.setText("Ara");
        txtAra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAraMouseClicked(evt);
            }
        });
        txtAra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAraKeyPressed(evt);
            }
        });
        jPanel3.add(txtAra);
        txtAra.setBounds(30, 30, 440, 30);

        btnAra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnAra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAraActionPerformed(evt);
            }
        });
        jPanel3.add(btnAra);
        btnAra.setBounds(500, 40, 50, 50);

        dcStart.setBackground(new java.awt.Color(207, 216, 220));
        dcStart.setDateFormatString("yyyy-MM-dd hh:mm");
        jPanel3.add(dcStart);
        dcStart.setBounds(30, 90, 210, 30);

        dcEnd.setBackground(new java.awt.Color(207, 216, 220));
        dcEnd.setDateFormatString("yyyy-MM-dd hh:mm");
        jPanel3.add(dcEnd);
        dcEnd.setBounds(270, 90, 200, 30);

        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Tarih Seçiniz");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(30, 70, 70, 14);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Konaklayanlar"));

        jTable1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setGridColor(new java.awt.Color(102, 102, 102));
        jTable1.setSelectionBackground(new java.awt.Color(187, 222, 251));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtAraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAraMouseClicked
        if (i == 0) {
            txtAra.setText("");
            txtAra.setForeground(Color.BLACK);
            i = 1;
        }
    }//GEN-LAST:event_txtAraMouseClicked

    private void txtAraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAraKeyPressed
        if (i == 0) {
            txtAra.setText("");
            txtAra.setForeground(Color.BLACK);
            i = 1;
        }
        search();
    }//GEN-LAST:event_txtAraKeyPressed

    private void btnAraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAraActionPerformed
        search();
    }//GEN-LAST:event_btnAraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAra;
    private com.toedter.calendar.JDateChooser dcEnd;
    private com.toedter.calendar.JDateChooser dcStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtAra;
    // End of variables declaration//GEN-END:variables
}
