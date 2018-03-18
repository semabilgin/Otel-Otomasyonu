/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import enums.StaffEnum;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import properties.StaffPro;
import properties.TownPro;

/**
 *
 * @author Huda
 */
public class pnlStaff extends javax.swing.JPanel {

    /**
     * Creates new form pnlStaff
     */
    pnlCustomers cs = new pnlCustomers();
    Validation vd = new Validation();
    ArrayList<ArrayList<TownPro>> townLs = new ArrayList<>();

    public pnlStaff() {
        initComponents();
        staffSelect();
        backGroundColor();
        townLs = cs.townLs;
        cs.cmbTownFill(cmbStaffTowns, cmbStaffCities);
        cs.cmbCitiesFill(cmbStaffCities);

    }

    Db db = new Db("oteldenemeleri", "root", "");
    LoggerClass lg = new LoggerClass();

    ArrayList<StaffPro> staffLs = new ArrayList<>();

    public void backGroundColor() {
        this.setBackground(new Color(119, 119, 119));
        pnlStaff.setBackground(new Color(119, 119, 119));
        pnlStafs.setBackground(new Color(119, 119, 119));
        txtStaffName.setBackground(new Color(160, 160, 160));
        txtStaffLName.setBackground(new Color(160, 160, 160));
        txtStaffidentity.setBackground(new Color(160, 160, 160));
        txtStaffPhone.setBackground(new Color(160, 160, 160));
        txtSalary.setBackground(new Color(160, 160, 160));// maas
        btnAdd.setBackground(new Color(75, 75, 75));
        btnDelete.setBackground(new Color(75, 75, 75));
        btnUpdate.setBackground(new Color(75, 75, 75));
        tblStaff.setBackground(new Color(160, 160, 160));

    }

    public void staffSelect() {
        staffLs.clear();
        try {
            ResultSet rs = db.connect().executeQuery("call staffSelect()");
            while (rs.next()) {
                StaffPro sp = new StaffPro();
                sp.setStaff_id(rs.getInt("" + StaffEnum.staff_id));
                sp.setFirst_name(rs.getString("" + StaffEnum.first_name));
                sp.setLast_name(rs.getString("" + StaffEnum.last_name));
                sp.setIdentity_number(rs.getString("" + StaffEnum.identity_number));
                sp.setPhone_number(rs.getString("" + StaffEnum.phone_number));
                sp.setStaff_title(rs.getString("" + StaffEnum.staff_title));
                sp.setStart_date(rs.getString("" + StaffEnum.start_date));
                sp.setQuit_date(rs.getString("" + StaffEnum.quit_date));
                sp.setSalary(rs.getInt("" + StaffEnum.salary));
                sp.setCity_name(rs.getString("city"));
                sp.setTown_name(rs.getString("town"));
                sp.setAddress(rs.getString("" + StaffEnum.address));
                staffLs.add(sp);
            }
            staffFill();
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("müşteri getirirken hata! " + ex);
        }
    }

    public void staffAdd() {
        try {

            PreparedStatement prs = db.preConnect("call staffInsert(?,?,?,?,?,?,?,?)");

            prs.setString(1, txtStaffName.getText().trim());
            prs.setString(2, txtStaffLName.getText().trim());
            prs.setString(3, txtStaffidentity.getText().trim());
            prs.setString(4, txtStaffPhone.getText().trim());
            prs.setString(5, "" + cmbStaffTitle.getSelectedItem());

            prs.setString(6, "" + txtSalary.getText());
            prs.setInt(7, townLs.get(cmbStaffCities.getSelectedIndex()).get(cmbStaffTowns.getSelectedIndex()).getTown_id());
            prs.setString(8, txtStaffAddress.getText().trim());
            int affected = prs.executeUpdate();
            if (affected > 0) {

                JOptionPane.showMessageDialog(this, "ekleme başarılı");
                staffSelect();
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("staff ekleme  başarısız! " + e);
        }

    }

    public void staffUpdate() {
        try {

            PreparedStatement prs = db.preConnect("call staffUpdate(?,?,?,?,?,?,?,?,?)");
            prs.setString(1, staffID);
            prs.setString(2, txtStaffName.getText().trim());
            prs.setString(3, txtStaffLName.getText().trim());
            prs.setString(4, txtStaffidentity.getText().trim());
            prs.setString(5, txtStaffPhone.getText().trim());
            prs.setString(6, "" + cmbStaffTitle.getSelectedItem());

            prs.setString(7, "" + txtSalary.getText());
            prs.setInt(8, 603/* townLs.get(cmbCities.getSelectedIndex()).get(cmbTown.getSelectedIndex()).getTown_id()*/);
            prs.setString(9, txtStaffAddress.getText().trim());
            int affected = prs.executeUpdate();
            if (affected > 0) {

                JOptionPane.showMessageDialog(this, "güncelleme başarılı");
                staffSelect();
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("staff guncelleme  başarısız! " + e);
        }
    }

    public void staffDelete() {

        try {

            int answer = JOptionPane.showConfirmDialog(this, "Silme işlemi gerçekleştirilsin mi?");
            if (answer == 0) {
                PreparedStatement prs = db.preConnect("call staffDelete(?)");
                prs.setInt(1, Integer.valueOf(staffID));

                int affected = prs.executeUpdate();
                if (affected > 0) {

                    JOptionPane.showMessageDialog(this, "silme başarılı");
                    staffSelect();
                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("staff silme başarısız! " + e);
        }
    }

    public void staffFill() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Adı");
        dtm.addColumn("Soyadı");
        dtm.addColumn("TC");
        dtm.addColumn("TEL");
        dtm.addColumn("Ünvan");
        dtm.addColumn("Giriş Tarihi");
        dtm.addColumn("Çıkış Tarihi");
        dtm.addColumn("Maaş");

        dtm.addColumn("İl");
        dtm.addColumn("İlçe");
        dtm.addColumn("Adres");
        for (StaffPro item : staffLs) {
            dtm.addRow(new String[]{String.valueOf(item.getStaff_id()), item.getFirst_name(), item.getLast_name(), item.getIdentity_number(), item.getPhone_number(), item.getStaff_title(), item.getStart_date(), item.getQuit_date(), String.valueOf(item.getSalary()), item.getCity_name(), item.getTown_name(), item.getAddress()});
        }
        tblStaff.setModel(dtm);
    }

    public String dateFormatting(Date dt) {
        String format = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        String date = sdf.format(dt);

        return date;
    }
    String staffID = "";

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlStaff = new javax.swing.JPanel();
        txtStaffName = new javax.swing.JTextField();
        txtStaffLName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtStaffidentity = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtStaffPhone = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbStaffTitle = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbStaffCities = new javax.swing.JComboBox<>();
        cmbStaffTowns = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtStaffAddress = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        pnlStafs = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStaff = new javax.swing.JTable();
        txtSalary = new javax.swing.JTextField();

        pnlStaff.setBorder(javax.swing.BorderFactory.createTitledBorder("Personel İşlemleri"));
        pnlStaff.setPreferredSize(new java.awt.Dimension(550, 550));

        jLabel1.setText("Adı");

        jLabel2.setText("Soyadı");

        jLabel3.setText("TC No");

        jLabel4.setText("Telefon");

        cmbStaffTitle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Resepsiyonist", "Temizlik görevlisi", "Bellboy", "Vale", "Barmen", "Güvenlik" }));

        jLabel7.setText("Ünvan");

        jLabel8.setText("Maaş");

        cmbStaffCities.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbStaffCities.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStaffCitiesItemStateChanged(evt);
            }
        });

        cmbStaffTowns.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("İl");

        jLabel10.setText("İlçe");

        txtStaffAddress.setColumns(20);
        txtStaffAddress.setRows(5);
        jScrollPane1.setViewportView(txtStaffAddress);

        jLabel11.setText("Adres");

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnAdd.setText("Ekle");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update.png"))); // NOI18N
        btnUpdate.setText("Güncelle");
        btnUpdate.setEnabled(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        btnDelete.setText("Sil");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        pnlStafs.setBorder(javax.swing.BorderFactory.createTitledBorder("Personeller"));
        pnlStafs.setPreferredSize(new java.awt.Dimension(551, 190));

        tblStaff.setModel(new javax.swing.table.DefaultTableModel(
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
        tblStaff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStaffMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblStaff);

        javax.swing.GroupLayout pnlStafsLayout = new javax.swing.GroupLayout(pnlStafs);
        pnlStafs.setLayout(pnlStafsLayout);
        pnlStafsLayout.setHorizontalGroup(
            pnlStafsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
        );
        pnlStafsLayout.setVerticalGroup(
            pnlStafsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStafsLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlStaffLayout = new javax.swing.GroupLayout(pnlStaff);
        pnlStaff.setLayout(pnlStaffLayout);
        pnlStaffLayout.setHorizontalGroup(
            pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStaffLayout.createSequentialGroup()
                .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStaffLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))
                                .addComponent(jLabel3))
                            .addComponent(jLabel4))
                        .addGap(40, 40, 40)
                        .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbStaffTitle, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtStaffName, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(txtStaffLName)
                            .addComponent(txtStaffidentity)
                            .addComponent(txtStaffPhone)
                            .addComponent(txtSalary))
                        .addGap(133, 133, 133)
                        .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStaffLayout.createSequentialGroup()
                                .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlStaffLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStaffLayout.createSequentialGroup()
                        .addContainerGap(219, Short.MAX_VALUE)
                        .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cmbStaffTowns, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbStaffCities, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStaffLayout.createSequentialGroup()
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlStaffLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlStafs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlStaffLayout.setVerticalGroup(
            pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStaffLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStaffName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel9)
                    .addComponent(cmbStaffCities, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStaffLName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cmbStaffTowns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlStaffLayout.createSequentialGroup()
                        .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtStaffidentity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtStaffPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbStaffTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlStaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlStafs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String name = txtStaffName.getText().trim();
        String lastname = txtStaffLName.getText().trim();
        String identity = txtStaffidentity.getText().trim();
        String phone = txtStaffPhone.getText().trim();
        String salary = txtSalary.getText().trim();
        backGroundColor();
        if (!vd.letterValidation(name)) {
            JOptionPane.showMessageDialog(this, "Adı alanı sadece harflerden oluşmalıdır!");
            txtStaffName.requestFocus();
            txtStaffName.setBackground(Color.pink);
        } else if (!vd.letterValidation(lastname)) {
            JOptionPane.showMessageDialog(this, "Soyadı alanı sadece harflerden oluşmalıdır!");
            txtStaffLName.requestFocus();
            txtStaffLName.setBackground(Color.pink);
        } else if (!vd.numberValidation(identity)) {
            JOptionPane.showMessageDialog(this, "TC no 11 haneli olmalı ve rakamlardan oluşmalıdır!");
            txtStaffidentity.requestFocus();
            txtStaffidentity.setBackground(Color.pink);
        } else if (!vd.numberValidation(phone)) {
            JOptionPane.showMessageDialog(this, "Telefon 05XX XXX XX XX şeklinde olmalıdır!");
            txtStaffPhone.requestFocus();
            txtStaffPhone.setBackground(Color.pink);
        } else if (!vd.decimalNumberValidation(salary)) {
            JOptionPane.showMessageDialog(this, "Maaş 1,000.00 şeklinde olmalıdır!");
            txtSalary.requestFocus();
            txtSalary.setBackground(Color.pink);
        } else {
            staffAdd();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void tblStaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStaffMouseClicked

        DateFormat df = new SimpleDateFormat("yyyy-dd-MM hh:mm");

        int row = tblStaff.getSelectedRow();
        if (row > -1) {
            staffID = String.valueOf(tblStaff.getValueAt(row, 0));
            txtStaffName.setText(String.valueOf(tblStaff.getValueAt(row, 1)));
            txtStaffLName.setText(String.valueOf(tblStaff.getValueAt(row, 2)));
            txtStaffidentity.setText(String.valueOf(tblStaff.getValueAt(row, 3)));
            txtStaffPhone.setText(String.valueOf(tblStaff.getValueAt(row, 4)));
            cmbStaffTitle.setSelectedItem((String.valueOf(tblStaff.getValueAt(row, 5))));

            txtSalary.setText(String.valueOf(tblStaff.getValueAt(row, 8)));
            cmbStaffCities.setSelectedItem((String.valueOf(tblStaff.getValueAt(row, 9))));
            cmbStaffTowns.setSelectedItem((String.valueOf(tblStaff.getValueAt(row, 10))));
            txtStaffAddress.append((String.valueOf(tblStaff.getValueAt(row, 11))));
        }
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);

    }//GEN-LAST:event_tblStaffMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String name = txtStaffName.getText().trim();
        String lastname = txtStaffLName.getText().trim();
        String identity = txtStaffidentity.getText().trim();
        String phone = txtStaffPhone.getText().trim();
        String salary = txtSalary.getText().trim();
        backGroundColor();
        if (!vd.letterValidation(name)) {
            JOptionPane.showMessageDialog(this, "Adı alanı sadece harflerden oluşmalıdır!");
            txtStaffName.requestFocus();
            txtStaffName.setBackground(Color.pink);
        } else if (!vd.letterValidation(lastname)) {
            JOptionPane.showMessageDialog(this, "Soyadı alanı sadece harflerden oluşmalıdır!");
            txtStaffLName.requestFocus();
            txtStaffLName.setBackground(Color.pink);
        } else if (!vd.numberValidation(identity)) {
            JOptionPane.showMessageDialog(this, "TC no 11 haneli olmalı ve rakamlardan oluşmalıdır!");
            txtStaffidentity.requestFocus();
            txtStaffidentity.setBackground(Color.pink);
        } else if (!vd.numberValidation(phone)) {
            JOptionPane.showMessageDialog(this, "Telefon 05XX XXX XX XX şeklinde olmalıdır!");
            txtStaffPhone.requestFocus();
            txtStaffPhone.setBackground(Color.pink);
        } else if (!vd.decimalNumberValidation(salary)) {
            JOptionPane.showMessageDialog(this, "Maaş 1,000.00 şeklinde olmalıdır!");
            txtSalary.requestFocus();
            txtSalary.setBackground(Color.pink);
        } else {
            staffUpdate();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        staffDelete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void cmbStaffCitiesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStaffCitiesItemStateChanged
        cs.cmbTownFill(cmbStaffTowns, cmbStaffCities);
    }//GEN-LAST:event_cmbStaffCitiesItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbStaffCities;
    private javax.swing.JComboBox<String> cmbStaffTitle;
    private javax.swing.JComboBox<String> cmbStaffTowns;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlStaff;
    private javax.swing.JPanel pnlStafs;
    private javax.swing.JTable tblStaff;
    private javax.swing.JTextField txtSalary;
    private javax.swing.JTextArea txtStaffAddress;
    private javax.swing.JTextField txtStaffLName;
    private javax.swing.JTextField txtStaffName;
    private javax.swing.JTextField txtStaffPhone;
    private javax.swing.JTextField txtStaffidentity;
    // End of variables declaration//GEN-END:variables
}
