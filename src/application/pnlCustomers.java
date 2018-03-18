/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import enums.CustomerEnum;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import properties.CustomerPro;
import properties.RoomPro;
import properties.TownPro;

/**
 *
 * @author Huda
 */
public class pnlCustomers extends javax.swing.JPanel {

    /**
     * Creates new form pnlCustomers
     */
    public pnlCustomers() {
        initComponents();
        cityTownLsFill();
        cmbCitiesFill(cmbCities);
        customerSelect();
        cmbTownFill(cmbTown, cmbCities);
        backGroundColor();
        btnHosting.setEnabled(false);
    }
    //yeni eklendi
    RoomPro room;

    public void backGroundColor() {
        this.setBackground(new Color(119, 119, 119));
        pnlCustomers.setBackground(new Color(119, 119, 119));
        pnlCustomer.setBackground(new Color(119, 119, 119));
        txtCustomerName.setBackground(new Color(160, 160, 160));
        txtCustomerLName.setBackground(new Color(160, 160, 160));
        txtCustomerPhone.setBackground(new Color(160, 160, 160));
        txtCustomerMail.setBackground(new Color(160, 160, 160));
        txtCustomerIdentity.setBackground(new Color(160, 160, 160));
        txtCustomerAddress.setBackground(new Color(160, 160, 160));
        btnCustomerUpdate.setBackground(new Color(75, 75, 75));
        btnCustomerDelete.setBackground(new Color(75, 75, 75));
        btnHosting.setBackground(new Color(75, 75, 75));
        btnAdd.setBackground(new Color(75, 75, 75));
        tblCustomers.setBackground(new Color(160, 160, 160));

    }

    public pnlCustomers(RoomPro rm) {
        initComponents();
        backGroundColor();
        cityTownLsFill();
        cmbCitiesFill(cmbCities);
        customerSelect();
        cmbTownFill(cmbTown, cmbCities);
        btnHosting.setEnabled(true);
        room = rm;
    }

    Db db = new Db("otelDenemeleri", "root", "");
    LoggerClass lg = new LoggerClass();
    Validation vd = new Validation();
    ArrayList<CustomerPro> customerLs = new ArrayList<>();

    public void customerSelect() {

        customerLs.clear();

        try {
            ResultSet rs = db.connect().executeQuery("call customerSelectPro()");
            while (rs.next()) {
                CustomerPro cpro = new CustomerPro();
                cpro.setCustomer_id(rs.getInt("" + CustomerEnum.customer_id));
                cpro.setFirst_name(rs.getString("" + CustomerEnum.first_name));
                cpro.setLast_name(rs.getString("" + CustomerEnum.last_name));
                cpro.setIdentity_number(rs.getString("" + CustomerEnum.identity_number));
                cpro.setPhone_number(rs.getString("" + CustomerEnum.phone_number));
                cpro.setEmail(rs.getString("" + CustomerEnum.email));
                cpro.setCity_name(rs.getString("name"));

                cpro.setTown_name(rs.getString(8));
                cpro.setAddress(rs.getString("" + CustomerEnum.address));

                customerLs.add(cpro);

            }
            customerFill(tblCustomers);
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("müşteri getirirken hata! " + ex);
        }
    }

    public void customerFill(JTable tbl) {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("ID");
        dtm.addColumn("Adı");
        dtm.addColumn("Soyadı");
        dtm.addColumn("TC");
        dtm.addColumn("TEL");
        dtm.addColumn("Mail");
        dtm.addColumn("İl");
        dtm.addColumn("İlçe");
        dtm.addColumn("Adres");
        for (CustomerPro item : customerLs) {
            dtm.addRow(new String[]{String.valueOf(item.getCustomer_id()), item.getFirst_name(), item.getLast_name(), item.getIdentity_number(), item.getPhone_number(), item.getEmail(), item.getCity_name(), item.getTown_name(), item.getAddress()});
        }
        tbl.setModel(dtm);
    }

    public void customerUpdate() {
        try {

            int answer = JOptionPane.showConfirmDialog(this, "güncelleme işlemi gerçekleştirilsin mi?");
            if (answer == 0) {
                PreparedStatement prs = db.preConnect("call customerUpdatePro(?,?,?,?,?,?,?,?)");
                prs.setInt(1, Integer.valueOf(customerID));
                prs.setString(2, txtCustomerName.getText().trim());
                prs.setString(3, txtCustomerLName.getText().trim());
                prs.setString(4, txtCustomerIdentity.getText().trim());
                prs.setString(5, txtCustomerPhone.getText().trim());
                prs.setString(6, txtCustomerMail.getText().trim());
                prs.setInt(7, townLs.get(cmbCities.getSelectedIndex()).get(cmbTown.getSelectedIndex()).getTown_id());
                prs.setString(8, txtCustomerAddress.getText().trim());
                int affected = prs.executeUpdate();
                if (affected > 0) {

                    JOptionPane.showMessageDialog(this, "güncelleme başarılı");
                    customerSelect();
                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("item gncelleme  başarısız! " + e);
        }
    }

    public void customerDelete() {

        try {

            int answer = JOptionPane.showConfirmDialog(this, "Silme işlemi gerçekleştirilsin mi?");
            if (answer == 0) {
                PreparedStatement prs = db.preConnect("call customerDeletePro(?)");
                prs.setInt(1, Integer.valueOf(customerID));

                int affected = prs.executeUpdate();
                if (affected > 0) {

                    JOptionPane.showMessageDialog(this, "Musteri silme islemi basarili.");
                    customerSelect();
                }
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("item gncelleme  başarısız! " + e);
        }
    }

    public void customerAdding() {
        try {

            PreparedStatement prs = db.preConnect("call customerInsertPro(?,?,?,?,?,?,?)");

            prs.setString(1, txtCustomerName.getText().trim());
            prs.setString(2, txtCustomerLName.getText().trim());
            prs.setString(3, txtCustomerIdentity.getText().trim());
            prs.setString(4, txtCustomerPhone.getText().trim());
            prs.setString(5, txtCustomerMail.getText().trim());
            prs.setInt(6, townLs.get(cmbCities.getSelectedIndex()).get(cmbTown.getSelectedIndex()).getTown_id());
            prs.setString(7, txtCustomerAddress.getText().trim());
            int affected = prs.executeUpdate();
            if (affected > 0) {

                JOptionPane.showMessageDialog(this, "ekleme başarılı");
                customerSelect();
            }

        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("customer ekleme  başarısız! " + e);
        }
    }
    ArrayList<String> cityIDList = new ArrayList<>();
    ArrayList<String> townIDList = new ArrayList<>();

    public void cmbCitiesFill(JComboBox<String> cmbCities) {
        cityIDList.clear();
        DefaultComboBoxModel dcmb = new DefaultComboBoxModel();
        try {
            ResultSet rs = db.connect().executeQuery("call citiesSelectPro()");
            while (rs.next()) {
                dcmb.addElement(rs.getString(2));
                cityIDList.add(rs.getString(1));
            }
            cmbCities.setModel(dcmb);

        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("cmbCities  getirirken hata! " + ex);
        }

    }

    public void cmbTownFill(JComboBox<String> cmbTown, JComboBox<String> cmbCities) {
        DefaultComboBoxModel dcmb = new DefaultComboBoxModel();
        for (TownPro townPro : townLs.get(cmbCities.getSelectedIndex())) {
            dcmb.addElement(townPro.getName());
        }
        cmbTown.setModel(dcmb);

    }

    ArrayList<ArrayList<TownPro>> townLs = new ArrayList<>();
    String customerID = "";

    public void cityTownLsFill() {
        ArrayList<TownPro> tLs = null;
        int a = 1;
        boolean b = true;
        try {

            ResultSet rs = db.connect().executeQuery("call townSelect()");
            while (rs.next()) {

                if (rs.getInt(2) == a + 1) {
                    b = true;
                    a = rs.getInt(2);
                }
                if (rs.getInt(2) == a && b == true) {
                    tLs = new ArrayList<>();
                    townLs.add(tLs);
                    b = false;
                }

                TownPro tp = new TownPro();
                tp.setCity_id(rs.getInt(2));
                tp.setTown_id(rs.getInt(1));
                tp.setName(rs.getString(3));

                tLs.add(tp);

            }
            townLs.add(tLs);

        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("deneme  getirirken hata! " + ex);
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

        pnlCustomers = new javax.swing.JPanel();
        txtCustomerName = new javax.swing.JTextField();
        txtCustomerLName = new javax.swing.JTextField();
        txtCustomerIdentity = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCustomerMail = new javax.swing.JTextField();
        txtCustomerPhone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbTown = new javax.swing.JComboBox<>();
        cmbCities = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCustomerAddress = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        btnCustomerUpdate = new javax.swing.JButton();
        btnCustomerDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        pnlCustomer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCustomers = new javax.swing.JTable();
        btnHosting = new javax.swing.JButton();

        pnlCustomers.setBorder(javax.swing.BorderFactory.createTitledBorder("Müşteri İşlemleri"));
        pnlCustomers.setPreferredSize(new java.awt.Dimension(550, 500));

        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Ad");

        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Soyad");

        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("TC No");

        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Telefon");
        jLabel4.setToolTipText("");

        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("e-Mail");

        cmbTown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbCities.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCities.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCitiesİtemStateChanged(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("İl");

        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("İlçe");

        txtCustomerAddress.setColumns(20);
        txtCustomerAddress.setRows(5);
        jScrollPane1.setViewportView(txtCustomerAddress);

        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Adres");

        btnCustomerUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/update.png"))); // NOI18N
        btnCustomerUpdate.setText("Güncelle");
        btnCustomerUpdate.setEnabled(false);
        btnCustomerUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerUpdateActionPerformed(evt);
            }
        });

        btnCustomerDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        btnCustomerDelete.setText("Sil");
        btnCustomerDelete.setEnabled(false);
        btnCustomerDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerDeleteActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        btnAdd.setText("Ekle");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        pnlCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder("Müşteriler"));
        pnlCustomer.setPreferredSize(new java.awt.Dimension(527, 150));

        tblCustomers.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCustomers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCustomersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCustomers);

        javax.swing.GroupLayout pnlCustomerLayout = new javax.swing.GroupLayout(pnlCustomer);
        pnlCustomer.setLayout(pnlCustomerLayout);
        pnlCustomerLayout.setHorizontalGroup(
            pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        pnlCustomerLayout.setVerticalGroup(
            pnlCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        btnHosting.setText("Oda Kaydi");
        btnHosting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHostingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlCustomersLayout = new javax.swing.GroupLayout(pnlCustomers);
        pnlCustomers.setLayout(pnlCustomersLayout);
        pnlCustomersLayout.setHorizontalGroup(
            pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCustomersLayout.createSequentialGroup()
                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlCustomersLayout.createSequentialGroup()
                                .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(13, 13, 13)
                                .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCustomerMail, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                    .addComponent(txtCustomerPhone)))
                            .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtCustomerIdentity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                                .addComponent(txtCustomerLName, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtCustomerName, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlCustomersLayout.createSequentialGroup()
                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCustomersLayout.createSequentialGroup()
                                .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(255, 255, 255)
                                .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlCustomersLayout.createSequentialGroup()
                                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(24, 24, 24)
                                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmbTown, 0, 190, Short.MAX_VALUE)
                                            .addComponent(cmbCities, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(pnlCustomersLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 32, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCustomersLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnCustomerUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCustomerDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pnlCustomer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCustomersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnHosting, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlCustomersLayout.setVerticalGroup(
            pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomersLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlCustomersLayout.createSequentialGroup()
                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCustomerLName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(pnlCustomersLayout.createSequentialGroup()
                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cmbCities, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cmbTown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCustomersLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnCustomerDelete)
                            .addComponent(btnCustomerUpdate)))
                    .addGroup(pnlCustomersLayout.createSequentialGroup()
                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCustomerIdentity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtCustomerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlCustomersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCustomerMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHosting, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCitiesİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCitiesİtemStateChanged
        cmbTownFill(cmbTown, cmbCities);
    }//GEN-LAST:event_cmbCitiesİtemStateChanged

    private void tblCustomersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomersMouseClicked
        int row = tblCustomers.getSelectedRow();
        if (row > -1) {
            customerID = String.valueOf(tblCustomers.getValueAt(row, 0));
            txtCustomerName.setText(String.valueOf(tblCustomers.getValueAt(row, 1)));
            txtCustomerLName.setText(String.valueOf(tblCustomers.getValueAt(row, 2)));
            txtCustomerIdentity.setText(String.valueOf(tblCustomers.getValueAt(row, 3)));
            txtCustomerPhone.setText(String.valueOf(tblCustomers.getValueAt(row, 4)));
            txtCustomerMail.setText(String.valueOf(tblCustomers.getValueAt(row, 5)));
            cmbCities.setSelectedItem(tblCustomers.getValueAt(row, 6));
            cmbTown.setSelectedItem(tblCustomers.getValueAt(row, 7));
            txtCustomerAddress.setText(String.valueOf(tblCustomers.getValueAt(row, 8)));
        }
        if (room != null) {
            btnHosting.setEnabled(true);
        }
        btnCustomerUpdate.setEnabled(true);
        btnCustomerDelete.setEnabled(true);
    }//GEN-LAST:event_tblCustomersMouseClicked

    private void btnCustomerUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerUpdateActionPerformed
        String name = txtCustomerName.getText().trim();
        String lastname = txtCustomerLName.getText().trim();
        String identity = txtCustomerIdentity.getText().trim();
        String phone = txtCustomerPhone.getText().trim();
        String mail = txtCustomerMail.getText().trim();

        if (!vd.letterValidation(name)) {
            JOptionPane.showMessageDialog(this, "Adı alanı sadece harflerden oluşmalıdır!");
        } else if (!vd.letterValidation(lastname)) {
            JOptionPane.showMessageDialog(this, "Soyadı alanı sadece harflerden oluşmalıdır!");
        } else if (!vd.numberValidation(identity)) {
            JOptionPane.showMessageDialog(this, "TC no 11 haneli olmalı ve rakamlardan oluşmalıdır!");
        } else if (!vd.numberValidation(phone)) {
            JOptionPane.showMessageDialog(this, "Telefon 05XX XXX XX XX şeklinde olmalıdır!");
        } else if (!vd.mailValidation(mail)) {
            JOptionPane.showMessageDialog(this, "Mail example@example.com şeklinde olmalıdır!");
        } else {
            customerUpdate();
        }
    }//GEN-LAST:event_btnCustomerUpdateActionPerformed

    private void btnCustomerDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerDeleteActionPerformed
        customerDelete();
    }//GEN-LAST:event_btnCustomerDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String name = txtCustomerName.getText().trim();
        String lastname = txtCustomerLName.getText().trim();
        String identity = txtCustomerIdentity.getText().trim();
        String phone = txtCustomerPhone.getText().trim();
        String mail = txtCustomerMail.getText().trim();
        txtCustomerName.setBackground(new Color(160, 160, 160));
        txtCustomerLName.setBackground(new Color(160, 160, 160));
        txtCustomerIdentity.setBackground(new Color(160, 160, 160));
        txtCustomerPhone.setBackground(new Color(160, 160, 160));
        txtCustomerMail.setBackground(new Color(160, 160, 160));
        
        if (!vd.letterValidation(name)) {
            JOptionPane.showMessageDialog(this, "Adı alanı sadece harflerden oluşmalıdır!");
            txtCustomerName.requestFocus();
            txtCustomerName.setBackground(Color.pink);
        } else if (!vd.letterValidation(lastname)) {
            JOptionPane.showMessageDialog(this, "Soyadı alanı sadece harflerden oluşmalıdır!");
            txtCustomerLName.requestFocus();
            txtCustomerLName.setBackground(Color.pink);
        } else if (!vd.numberValidation(identity)) {
            JOptionPane.showMessageDialog(this, "TC no 11 haneli olmalı ve rakamlardan oluşmalıdır!");
            txtCustomerIdentity.requestFocus();
            txtCustomerIdentity.setBackground(Color.pink);
        } else if (!vd.numberValidation(phone)) {
            JOptionPane.showMessageDialog(this, "Telefon 05XX XXX XX XX şeklinde olmalıdır!");
            txtCustomerPhone.requestFocus();
            txtCustomerPhone.setBackground(Color.pink);
        } else if (!vd.mailValidation(mail)) {
            JOptionPane.showMessageDialog(this, "Mail example@example.com şeklinde olmalıdır!");
            txtCustomerMail.requestFocus();
            txtCustomerMail.setBackground(Color.pink);
        } else {
            customerAdding();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnHostingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHostingActionPerformed
        new RoomDesign(room).setVisible(true);
    }//GEN-LAST:event_btnHostingActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCustomerDelete;
    private javax.swing.JButton btnCustomerUpdate;
    private javax.swing.JButton btnHosting;
    private javax.swing.JComboBox<String> cmbCities;
    private javax.swing.JComboBox<String> cmbTown;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlCustomer;
    private javax.swing.JPanel pnlCustomers;
    private javax.swing.JTable tblCustomers;
    private javax.swing.JTextArea txtCustomerAddress;
    private javax.swing.JTextField txtCustomerIdentity;
    private javax.swing.JTextField txtCustomerLName;
    private javax.swing.JTextField txtCustomerMail;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtCustomerPhone;
    // End of variables declaration//GEN-END:variables
}
