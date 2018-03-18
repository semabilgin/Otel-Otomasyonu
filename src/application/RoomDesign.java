package application;

import enums.CustomerEnum;
import enums.RoomEnum;
import enums.TypeEnum;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import properties.CustomerPro;
import properties.RoomPro;
import properties.TypePro;

public class RoomDesign extends javax.swing.JFrame {

    public RoomDesign() {
        initComponents();

    }
    Db db = new Db("otelDenemeleri", "root", "");
    RoomPro room;
    String[] roomAvalibleLs = {"", "DOLU", "DOLU", "BOS", "BOS"};
    String[] roomCleanLs = {"", "TEMIZ", "KIRLI", "TEMIZ", "KIRLI"};
    ArrayList<CustomerPro> customerLs = new ArrayList<>();
    HotelOtomation hotelO = new HotelOtomation();
LoggerClass lg = new LoggerClass();
    public RoomDesign(RoomPro rm) {
        initComponents();
        room = rm;
        customerLs = hotelO.customerLs;
        hotelO.customerFill(tblCustomers, customerLs);
        refreshRoomInfo();
        typeSelect(typeLs);
        cmbTypeFill(cmbType);
        roomSelectEmpty();
        backGroundColor();
        if (jLabel2.getText().equals("BOS")) {
            jButton2.setEnabled(false);
        }
    }

    public void backGroundColor() {
        getContentPane().setBackground(new Color(119, 119, 119));
        jPanel1.setBackground(new Color(119, 119, 119));
        jPanel2.setBackground(new Color(119, 119, 119));
        jPanel3.setBackground(new Color(119, 119, 119));
        jTextField1.setBackground(new Color(160, 160, 160));
        jButton1.setBackground(new Color(75, 75, 75));
        jButton2.setBackground(new Color(75, 75, 75));
        btnHostAdd.setBackground(new Color(75, 75, 75));
        jButton4.setBackground(new Color(75, 75, 75));
        jButton5.setBackground(new Color(75, 75, 75));
        tblCustomers.setBackground(new Color(160, 160, 160));
        tblType.setBackground(new Color(160, 160, 160));
    }

    String state;

    public void stateDetermine() {
        if (room.getState().equals("1") || room.getState().equals("2")) {
            state = (cmbCleanSt.getSelectedIndex() + 1) + "";
        } else if (room.getState().equals("3") || room.getState().equals("4")) {
            state = (cmbCleanSt.getSelectedIndex() + 3) + "";
        }
        room.setState(state);
    }

    public void refreshRoomInfo() {
        jLabel1.setText(room.getNumber());
        jLabel2.setText(roomAvalibleLs[Integer.valueOf(room.getState())] + "");
        jLabel3.setText(roomCleanLs[Integer.valueOf(room.getState())] + "");
        jLabel4.setText(room.getType());
    }

    public void roomStateUpdate() {
        stateDetermine();

        try {
            int ans = db.connect().executeUpdate("call roomUpdateStatePro(" + room.getRoom_id() + "," + room.getState() + ")");
            if (ans > 0) {
//                System.out.println("state degisimi gerceklesti");
                refreshRoomInfo();
            }
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("State degistirme hatasi" + e);
        }
    }
    ArrayList<CustomerPro> customerSearchLs = new ArrayList<>();
    public ArrayList<TypePro> typeLs = new ArrayList<>();

    boolean close = true;

    public void customerSearch() {
        String search = jTextField1.getText().trim() + "*";
        try {
            PreparedStatement pr = db.preConnect("call customerSearchPro(?)");
            pr.setString(1, search);
            ResultSet rs = pr.executeQuery();
            customerSearchLs.clear();

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
                customerSearchLs.add(cpro);

            }
            if (customerSearchLs.isEmpty()) {
                int ans = JOptionPane.showConfirmDialog(this, "Aradiginiz kriterde musteri bulunamadi musteri ekleme sayfasina gitmek istermisiniz?");
                if (ans == 0) {
                    close = false;
                    hotelO.showPage(new pnlCustomers(room));
                    hotelO.setVisible(true);
                    this.dispose();
                }
            }

        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("search custemer error" + e);
        }
    }

    public void hostCustomerEnter() {
        int customerId = Integer.valueOf(tblCustomers.getValueAt(tblCustomers.getSelectedRow(), 0) + "");
        int sale = 0;

        if (jTextField2.getText().trim().equals("")) {
            sale = 0;
        } else {
            sale = Integer.valueOf(jTextField2.getText());
        }
        try {
            PreparedStatement prs = db.preConnect("call hostingCustomerEnterPro(?,?,?)");
            prs.setInt(1, customerId);
            prs.setInt(2, room.getRoom_id());
            prs.setInt(3, sale);
            int ans = prs.executeUpdate();
            if (ans > 0) {
//                System.out.println("Host ekleme basarili");
            }

        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("Host ekleme hatasi" + e);
        }
    }

    public void hostCustomerExit() {
        try {
            PreparedStatement prs = db.preConnect("call hostingCustomerExitPro(?)");
            prs.setInt(1, room.getRoom_id());
            int ans = prs.executeUpdate();
            if (ans > 0) {
                JOptionPane.showMessageDialog(this, "Host cikarma basarili");
            }

        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("Host ekleme hatasi" + e);
        }
    }

    public void typeSelect(ArrayList<TypePro> typeLs) {

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
            cmbTypeFill(cmbType);
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("item getirirken hata! " + ex);
        }
    }

    public void cmbTypeFill(JComboBox<String> cmbType) {

        DefaultComboBoxModel dcbm = new DefaultComboBoxModel();
        dcbm.addElement("Tumu");
        for (TypePro item : typeLs) {
            dcbm.addElement(item.getType());
        }
        cmbType.setModel(dcbm);
    }
    ArrayList<RoomPro> emptyRoom = new ArrayList<>();

    public void roomSelectEmpty() {
        emptyRoom.clear();
        PreparedStatement pr = db.preConnect("call roomSelectEmptyPro(?,?)");
        int secilenType = cmbType.getSelectedIndex();
        try {
            pr.setInt(1, secilenType);
            pr.setInt(2, room.getRoom_id());
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                RoomPro rp = new RoomPro();
                rp.setRoom_id(rs.getInt(RoomEnum.room_id + ""));
                rp.setNumber(rs.getString(RoomEnum.number + ""));
                rp.setType(rs.getString(RoomEnum.type + ""));
                rp.setPrice(rs.getFloat(RoomEnum.price + ""));
                emptyRoom.add(rp);
            }
            fillEmptyRoomTable();
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("select empty room error:" + ex);
        }

    }

    public void fillEmptyRoomTable() {

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("Id");
        dtm.addColumn("Oda Numarasi");
        dtm.addColumn("Oda Turu");
        dtm.addColumn("Fiyat");
        for (RoomPro rm : emptyRoom) {
            dtm.addRow(new String[]{rm.getRoom_id() + "", rm.getNumber(), rm.getType(), rm.getPrice() + ""});
        }
        tblType.setModel(dtm);
    }

    public void hostCustomerUpdate() {
        int secilen = Integer.valueOf(tblType.getValueAt(tblType.getSelectedRow(), 0) + "");
        PreparedStatement pr = db.preConnect("call hostingCustomerUpdateRoomPro(?,?)");
        try {
            pr.setInt(1, room.getRoom_id());
            pr.setInt(2, secilen);
//            System.out.println("roomid:" + room.getRoom_id() + "secilen:" + secilen);
            int ans = pr.executeUpdate();
            if (ans > 0) {
//                System.out.println("hostCustomerUpdate basarili");
                JOptionPane.showMessageDialog(this, "Oda Tasima Islemi Basarili");
                new HotelOtomation().setVisible(true);
            }
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("hostCustomerUpdate hatasi" + e);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cmbCleanSt = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblType = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        cmbType = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btnHostAdd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustomers = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Oda Düzenleme");
        setBackground(new java.awt.Color(119, 119, 119));
        setLocation(new java.awt.Point(225, 50));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Odada Bilgileri"));

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        jLabel4.setText("jLabel4");

        jLabel1.setText("jLabel1");

        cmbCleanSt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TEMiZ", "KIRLI" }));
        cmbCleanSt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCleanStItemStateChanged(evt);
            }
        });

        jButton1.setText("Duzenle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(cmbCleanSt, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(28, 28, 28))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbCleanSt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(37, 37, 37))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Oda Tasima "));

        tblType.setModel(new javax.swing.table.DefaultTableModel(
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
        tblType.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTypeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblType);

        jButton5.setText("Odaya Tasi");
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        cmbType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTypeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Konaklayan Musteri Ekle"));

        jLabel5.setText("MUSTERI BILGISI");

        jButton2.setText("Odayi Bosalt");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnHostAdd.setText("Odaya Kaydet");
        btnHostAdd.setEnabled(false);
        btnHostAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHostAddActionPerformed(evt);
            }
        });

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
        jScrollPane1.setViewportView(tblCustomers);

        jButton4.setText("ARA");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel6.setText("Indirim Yüzdesi       %");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHostAdd))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addGap(150, 150, 150)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jLabel6)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHostAdd)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        roomStateUpdate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

        if (close) {
            new HotelOtomation().setVisible(true);
        }
    }//GEN-LAST:event_formWindowClosed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        if (jTextField1.getText().trim().equals("")) {
            hotelO.customerSelect();
            hotelO.customerFill(tblCustomers, customerLs);
        } else {
            customerSearch();
            hotelO.customerFill(tblCustomers, customerSearchLs);
        }

// tamami silinince arama yapacak

    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnHostAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHostAddActionPerformed

        if (tblCustomers.getSelectedRow() > 0) {
            if (jTextField2.getText().trim().equals("")) {
                int ans = JOptionPane.showConfirmDialog(this, "Indirim yuzdesi girilmeden devam edilsin mi?");
                if (ans == 0) {
                    hostCustomerEnter();
                }

            } else {
                hostCustomerEnter();
            }

            JOptionPane.showMessageDialog(this, "Musteri Ekleme Isleminiz Gerceklesti!");
        } else {
            JOptionPane.showMessageDialog(this, "Lutfen Once Musteri Secimi Yapiniz!");
        }

    }//GEN-LAST:event_btnHostAddActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        hostCustomerExit();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cmbTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTypeItemStateChanged
        roomSelectEmpty();
    }//GEN-LAST:event_cmbTypeItemStateChanged

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        hostCustomerUpdate();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void cmbCleanStItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCleanStItemStateChanged
        stateDetermine();
    }//GEN-LAST:event_cmbCleanStItemStateChanged

    private void tblCustomersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomersMouseClicked
        if (!jLabel2.getText().equals("DOLU")) {
            btnHostAdd.setEnabled(true);
        }
    }//GEN-LAST:event_tblCustomersMouseClicked

    private void tblTypeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTypeMouseClicked
        jButton5.setEnabled(true);
    }//GEN-LAST:event_tblTypeMouseClicked

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RoomDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoomDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoomDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoomDesign.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new RoomDesign().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHostAdd;
    private javax.swing.JComboBox<String> cmbCleanSt;
    private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable tblCustomers;
    private javax.swing.JTable tblType;
    // End of variables declaration//GEN-END:variables

}
