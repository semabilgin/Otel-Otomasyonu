/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import enums.ItemEnum;
import enums.TypeEnum;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import properties.TypePro;

/**
 *
 * @author Huda
 */
public class pnlRoomAdd extends javax.swing.JPanel {

    /**
     * Creates new form pnlRoomAdd
     */
    public pnlRoomAdd() {
        initComponents();
        typeSelect();
        itemAdd();
        backGroundColor();
    }

    Db db = new Db("oteldenemeleri", "root", "");
    LoggerClass lg = new LoggerClass();

    public ArrayList<TypePro> typeLs = new ArrayList<>();
public void backGroundColor(){
    this.setBackground(new Color(119, 119, 119));
    jPanel1.setBackground(new Color(119, 119, 119));
    pnlitem.setBackground(new Color(119, 119, 119));
    txtRoomNumber.setBackground(new Color(160, 160, 160));
    jButton3.setBackground(new Color(75, 75, 75));
    jButton1.setBackground(new Color(75, 75, 75));
}
    public void typeSelect() {

        typeLs.clear();

        try {
            ResultSet rs = db.connect().executeQuery("call typeSelectPro()");
            while (rs.next()) {
                TypePro tpro = new TypePro();
                tpro.setType_id(rs.getInt("" + TypeEnum.type_id));
                tpro.setType(rs.getString("" + TypeEnum.type));
                tpro.setPrice(rs.getFloat("" + TypeEnum.price));
                typeLs.add(tpro);
            }
            //  jTable1.setModel(dtm);
            cmbTypeFill();
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("item getirirken hata! " + ex);
        }
    }

    public void cmbTypeFill() {

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        for (TypePro item : typeLs) {
            dcbm.addElement(item.getType());
        }
        cmbType.setModel(dcbm);
    }

    public void roomAdd() {
        int room_id = -1;
        try {
            PreparedStatement prs = db.preConnect("{ call roomInsertPro(?,?) }");
            prs.setString(1, txtRoomNumber.getText().trim());
            prs.setInt(2, typeLs.get(cmbType.getSelectedIndex()).getType_id());

            ResultSet rs = prs.executeQuery();
            if (rs.next()) {
                room_id = rs.getInt(1);
                roomItemAdd(room_id);
                JOptionPane.showMessageDialog(this, "Oda ekleme başarılı.");
                
            }

        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("data getirme hatası : " + e);
        }
    }

    public void roomItemAdd(int room_id) {
//chx.getName().substring(3, 4)
        Component[] dizi = pnlitem.getComponents();
        for (Component item : dizi) {
            if (item instanceof JCheckBox) {
                JCheckBox chx = (JCheckBox) item;

                if (chx.isSelected()) {
                    String id = chx.getName().substring(3);
//                    System.out.println(id);
                    try {
                        PreparedStatement pr = db.preConnect("{ call roomInsertTypePro(?,?) }");
                        pr.setInt(1, room_id);
                        pr.setInt(2, Integer.valueOf(id));
                        int affected = pr.executeUpdate();
                        if (affected > 0) {

                            // JOptionPane.showMessageDialog(this, "ekleme başarılı");
                        }

                    } catch (HeadlessException | SQLException ex) {
                        lg.loggerFunction(ex);
//                        System.err.println("Room Item Add hatası : " + e);
                    } finally {
                        db.disconnect();
                    }

                }
            }

        }
    }

    public void itemAdd() {
        try {
            ResultSet rs = db.connect().executeQuery("call itemSelectPro()");
            int x = 15, y = 25;
//            System.out.println("item ad'a gir");
            while (rs.next()) {

                if ((x + 150) > pnlitem.getSize().width) {
                    y += 40;
                    x = 15;

                }

                JCheckBox ch = new JCheckBox();

                ch.setText(rs.getString("" + ItemEnum.name));
                ch.setLocation(x, y);
                ch.setSize(150, 20);
                ch.setName("chx" + rs.getInt("" + ItemEnum.item_id));

                x += 200;
//                pnlitem.setLayout(null);
                pnlitem.add(ch);
                this.invalidate();
                this.validate();
                this.repaint();

            }

        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("item getirirken hata! " + ex);
        }
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
        pnlitem = new javax.swing.JPanel();
        txtRoomNumber = new javax.swing.JTextField();
        cmbType = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Oda İşlemleri"));
        jPanel1.setPreferredSize(new java.awt.Dimension(580, 510));

        pnlitem.setBorder(javax.swing.BorderFactory.createTitledBorder("Demirbaş Listesi"));
        pnlitem.setLayout(new java.awt.GridLayout(0, 4, 5, 5));

        jLabel1.setText("Oda Numarası");

        jLabel2.setText("Oda Türü");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update.png"))); // NOI18N
        jButton3.setText("Güncelle");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        jButton1.setText("Ekle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlitem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 270, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(pnlitem, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        roomAdd();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlitem;
    private javax.swing.JTextField txtRoomNumber;
    // End of variables declaration//GEN-END:variables
}
