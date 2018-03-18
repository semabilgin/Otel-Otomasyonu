/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import enums.ItemEnum;
import enums.TypeEnum;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import properties.ItemPro;
import properties.TypePro;

/**
 *
 * @author Huda
 */
public class pnlTypeItem extends javax.swing.JPanel {

    /**
     * Creates new form pnlTypeItem
     */
    Db db = new Db("oteldenemeleri", "root", "");
    LoggerClass lg = new LoggerClass();

    public pnlTypeItem() {
        initComponents();
        backGroundColor();
        selectItem();
        typeSelect();
    }

    public void itemAdding() {

        try {
            PreparedStatement pr = db.preConnect("call itemInsertPro(?);");
            pr.setString(1, txtitem.getText().trim());

            int affected = pr.executeUpdate();
            if (affected > 0) {

                JOptionPane.showMessageDialog(this, "ekleme başarılı");
                selectItem();
            }

        } catch (HeadlessException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("data getirme hatası : " + e);
        } finally {
            db.disconnect();
        }

    }

    ArrayList<ItemPro> alItem = new ArrayList<>();

    public void backGroundColor() {
        this.setBackground(new Color(119, 119, 119));
        jPanel1.setBackground(new Color(119, 119, 119));
        jPanel2.setBackground(new Color(119, 119, 119));
        txtPrice.setBackground(new Color(160, 160, 160));
        txtType.setBackground(new Color(160, 160, 160));
        txtitem.setBackground(new Color(160, 160, 160));
        jButton1.setBackground(new Color(75, 75, 75));
        jButton2.setBackground(new Color(75, 75, 75));
        btnItemDelete.setBackground(new Color(75, 75, 75));
        btnItemUpdate.setBackground(new Color(75, 75, 75));
        btnTypeDelete1.setBackground(new Color(75, 75, 75));
        btnTypeUpdate1.setBackground(new Color(75, 75, 75));

    }

    public void selectItem() {
        alItem.clear();

        try {
            ResultSet rs = db.connect().executeQuery("call itemSelectPro();");
            while (rs.next()) {
                ItemPro ipro = new ItemPro();
                ipro.setItem_id(rs.getInt("" + ItemEnum.item_id));
                ipro.setName(rs.getString("" + ItemEnum.name));
                alItem.add(ipro);
            }
            //  jTable1.setModel(dtm);
            ItemFill();
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("item getirirken hata! " + ex);
        }

    }

    public void ItemFill() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Demirbaş");
        for (ItemPro itemPro : alItem) {
            dtm.addRow(new String[]{"" + itemPro.getItem_id(), itemPro.getName()});
        }
        tblitems.setModel(dtm);
    }

    public void itemDelete() {

        try {

            int answer = JOptionPane.showConfirmDialog(this, "silme işlemi gerçekleştirilsin mi?");
            if (answer == 0) {
                PreparedStatement prs = db.preConnect("call itemDeletePro(?);");
                prs.setInt(1, Integer.valueOf(itemID));
                int affected = prs.executeUpdate();
                if (affected > 0) {

                    JOptionPane.showMessageDialog(this, "silme başarılı");
                    selectItem();
                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("item silme başarısız! " + e);
        }

    }

    public void itemUpdate() {
        try {

            int answer = JOptionPane.showConfirmDialog(this, "güncelleme işlemi gerçekleştirilsin mi?");
            if (answer == 0) {
                PreparedStatement prs = db.preConnect("call itemUpdatePro(?,?);");
                prs.setInt(1, Integer.valueOf(itemID));
                prs.setString(2, txtitem.getText().trim());
                int affected = prs.executeUpdate();
                if (affected > 0) {

                    JOptionPane.showMessageDialog(this, "güncelleme başarılı");
                    selectItem();
                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("item gncelleme  başarısız! " + e);
        }

    }

    public ArrayList<TypePro> typeLs = new ArrayList<>();

    public void typeSelect() {

        typeLs.clear();

        try {
            ResultSet rs = db.connect().executeQuery("call typeSelectPro();");
            while (rs.next()) {
                TypePro tpro = new TypePro();
                tpro.setType_id(rs.getInt("" + TypeEnum.type_id));
                tpro.setType(rs.getString("" + TypeEnum.type));
                tpro.setPrice(rs.getFloat("" + TypeEnum.price));
                typeLs.add(tpro);
            }
            //  jTable1.setModel(dtm);
            typeFill();
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("item getirirken hata! " + ex);
        }
    }

    public void typeFill() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Oda Tipi");
        dtm.addColumn("Fiyat");
        for (TypePro item : typeLs) {
            dtm.addRow(new String[]{"" + item.getType_id(), item.getType(), "" + item.getPrice()});
        }
        tblTypes.setModel(dtm);
    }

    public void typeAdd() {

        try {
            PreparedStatement pr = db.preConnect(" call typeInsertPro(?,?); ");
            pr.setString(1, txtType.getText().trim());
            pr.setFloat(2, Float.valueOf(txtPrice.getText().trim()));
            int affected = pr.executeUpdate();
            if (affected > 0) {

                JOptionPane.showMessageDialog(this, "oda tıpı ekleme başarılı");

            }

        } catch (HeadlessException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("oda tıpı ekleme hatası : " + e);
        } finally {
            db.disconnect();
        }

    }

    public void typeDelete() {
        try {

            int answer = JOptionPane.showConfirmDialog(this, "silme işlemi gerçekleştirilsin mi?");
            if (answer == 0) {
                PreparedStatement prs = db.preConnect("call typeDeletePro(?);");
                prs.setInt(1, Integer.valueOf(typeId));
                int affected = prs.executeUpdate();
                if (affected > 0) {

                    JOptionPane.showMessageDialog(this, "silme başarılı");
                    typeSelect();
                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("type silme başarısız! " + e);
        }
    }

    public void typeUpdate() {
        try {

            int answer = JOptionPane.showConfirmDialog(this, "güncelleme işlemi gerçekleştirilsin mi?");
            if (answer == 0) {
                PreparedStatement prs = db.preConnect("call typeUpdatePro(?,?,?);");
                prs.setInt(1, Integer.valueOf(typeId));
                prs.setString(2, txtType.getText().trim());
                prs.setFloat(3, Float.valueOf(txtPrice.getText().trim()));
                int affected = prs.executeUpdate();
                if (affected > 0) {

                    JOptionPane.showMessageDialog(this, "güncelleme başarılı");
                    typeSelect();
                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("type gncelleme  başarısız! " + e);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblitems = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnItemUpdate = new javax.swing.JButton();
        btnItemDelete = new javax.swing.JButton();
        txtitem = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtType = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btnTypeUpdate1 = new javax.swing.JButton();
        btnTypeDelete1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTypes = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(600, 600));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Demirbaş"));
        jPanel1.setPreferredSize(new java.awt.Dimension(290, 466));

        tblitems.setModel(new javax.swing.table.DefaultTableModel(
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
        tblitems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblitemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblitems);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Demirbaş Adı");

        btnItemUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update.png"))); // NOI18N
        btnItemUpdate.setEnabled(false);
        btnItemUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemUpdateActionPerformed(evt);
            }
        });

        btnItemDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        btnItemDelete.setEnabled(false);
        btnItemDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnItemUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnItemDelete))
                            .addComponent(jLabel1)
                            .addComponent(txtitem, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtitem, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnItemUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnItemDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Oda Tipi"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Oda Tipi");

        jLabel3.setText("Fiyat");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnTypeUpdate1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update.png"))); // NOI18N
        btnTypeUpdate1.setEnabled(false);
        btnTypeUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTypeUpdate1ActionPerformed(evt);
            }
        });

        btnTypeDelete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        btnTypeDelete1.setEnabled(false);
        btnTypeDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTypeDelete1ActionPerformed(evt);
            }
        });

        tblTypes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTypes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTypesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblTypes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(txtPrice)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTypeUpdate1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTypeDelete1))
                            .addComponent(txtType))
                        .addGap(0, 60, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTypeUpdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTypeDelete1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(123, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblitemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblitemsMouseClicked

        int row = tblitems.getSelectedRow();
        if (row > -1) {
            itemID = String.valueOf(tblitems.getValueAt(row, 0));
            txtitem.setText(String.valueOf(tblitems.getValueAt(row, 1)));
        }

        btnItemDelete.setEnabled(true);
        btnItemUpdate.setEnabled(true);
    }//GEN-LAST:event_tblitemsMouseClicked
    String itemID = "";
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        itemAdding();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnItemUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemUpdateActionPerformed
        itemUpdate();
    }//GEN-LAST:event_btnItemUpdateActionPerformed

    private void btnItemDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemDeleteActionPerformed

        itemDelete();
    }//GEN-LAST:event_btnItemDeleteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        typeAdd();
        typeSelect();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnTypeUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTypeUpdate1ActionPerformed
        typeUpdate();

    }//GEN-LAST:event_btnTypeUpdate1ActionPerformed

    private void btnTypeDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTypeDelete1ActionPerformed
        typeDelete();
    }//GEN-LAST:event_btnTypeDelete1ActionPerformed
    String typeId = "";
    private void tblTypesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTypesMouseClicked
        int row = tblTypes.getSelectedRow();
        if (row > -1) {
            typeId = String.valueOf(tblTypes.getValueAt(row, 0));
            txtType.setText(String.valueOf(tblTypes.getValueAt(row, 1)));
            txtPrice.setText(String.valueOf(tblTypes.getValueAt(row, 2)));
        }

        btnTypeDelete1.setEnabled(true);
        btnTypeUpdate1.setEnabled(true);
    }//GEN-LAST:event_tblTypesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnItemDelete;
    private javax.swing.JButton btnItemUpdate;
    private javax.swing.JButton btnTypeDelete1;
    private javax.swing.JButton btnTypeUpdate1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblTypes;
    private javax.swing.JTable tblitems;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtType;
    private javax.swing.JTextField txtitem;
    // End of variables declaration//GEN-END:variables
}
