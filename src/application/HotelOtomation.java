package application;

import enums.CustomerEnum;
import enums.RoomEnum;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import properties.CustomerPro;
import properties.RoomPro;
import properties.UserPro;

public class HotelOtomation extends javax.swing.JFrame {

    public HotelOtomation() {
        initComponents();
        roomRead();
        roomCreat();
        customerSelect();
        backGroundColor();

    }
        public HotelOtomation(UserPro up) {
        initComponents();
        roomRead();
        roomCreat();
        customerSelect();
        backGroundColor();
            if (up.getAuthory().equals("0")) {
                //resepsiyonist yetkisi sifir
                btnUsers.setEnabled(false);
                btnSettings.setEnabled(false);
            }

    }
    Db db = new Db("otelDenemeleri", "root", "");
    LoggerClass lg = new LoggerClass();

    ArrayList<RoomPro> roomLs = new ArrayList<>();
    String[] roomAvalibleLs = {"", "DOLU", "DOLU", "BOS", "BOS"};
    String[] roomCleanLs = {"", "TEMIZ", "KIRLI", "TEMIZ", "KIRLI"};

    ArrayList<CustomerPro> customerLs = new ArrayList<>();


    
    
    
    public void backGroundColor() {
        getContentPane().setBackground(new Color(68, 68, 68));
        pnlMain.setBackground(new Color(119, 119, 119));
        jPanel1.setBackground(new Color(119, 119, 119));
        jScrollPane1.setBackground(new Color(119, 119, 119));
        btnRooms.setBackground(new Color(45, 45, 45));
        btnHosting.setBackground(new Color(45, 45, 45));
        btnBooking.setBackground(new Color(45, 45, 45));
        btnShowBooking.setBackground(new Color(45, 45, 45));
        btnCustomers.setBackground(new Color(45, 45, 45));
        btnStaff.setBackground(new Color(45, 45, 45));
        btnBills.setBackground(new Color(45, 45, 45));
        btnAddItem.setBackground(new Color(45, 45, 45));
        btnAddRoom.setBackground(new Color(45, 45, 45));
        btnUsers.setBackground(new Color(45, 45, 45));
        btnSettings.setBackground(new Color(45, 45, 45));
        btnPassword.setBackground(new Color(45, 45, 45));
        btnLogout.setBackground(new Color(45, 45, 45));

    }

    public void customerSelect() {

        customerLs.clear();

        try {
            ResultSet rs = db.connect().executeQuery("call customerSelectPro();");
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

        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("müşteri getirirken hata! " + ex);
        }
    }

    public void customerFill(JTable tbl, ArrayList<CustomerPro> ls) {
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
        for (CustomerPro item : ls) {
            dtm.addRow(new String[]{String.valueOf(item.getCustomer_id()), item.getFirst_name(), item.getLast_name(), item.getIdentity_number(), item.getPhone_number(), item.getEmail(), item.getCity_name(), item.getTown_name(), item.getAddress()});
        }
        tbl.setModel(dtm);
    }

    public void showPage(JPanel panel) {
        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(panel);
        jPanel1.repaint();
        jPanel1.revalidate();
    }

    public void roomCreat() {
        pnlMain.setVisible(true);
        int x = 12, y = 10;
        for (RoomPro room : roomLs) {

            if (x + 140 > pnlMain.getSize().width) {
                x = 12;
                y += 100;

            }
            if (y + 100 > pnlMain.getSize().height) {
                pnlMain.setPreferredSize(new Dimension(pnlMain.getSize().width, y + 110));
            }

            JPanel panel = new JPanel();
            panel.setName("panel" + room.getNumber());
            panel.setBorder(new TitledBorder(room.getNumber() + " Nolu " + room.getType() + " Oda"));
            panel.setSize(140, 100);
            panel.setLocation(x, y);
            panel.setVisible(true);
            panel.setBackground(new Color(119, 119, 119));

            JLabel label = new JLabel();
            label.setSize(130, 18);
            label.setLocation(5, 22);
            label.setOpaque(true);
            label.setText("           ODA " + roomAvalibleLs[Integer.valueOf(room.getState())]);

            if (Integer.valueOf(room.getState()) == 1 || Integer.valueOf(room.getState()) == 2) {
                label.setBackground(new Color(204, 0, 0));
            }
            if (Integer.valueOf(room.getState()) == 3 || Integer.valueOf(room.getState()) == 4) {
                label.setBackground(new Color(0, 126, 51));
            }

            JLabel label1 = new JLabel();
            label1.setSize(130, 18);
            label1.setLocation(5, 43);
            label1.setOpaque(true);
            label1.setText("               " + roomCleanLs[Integer.valueOf(room.getState())]);
            if (Integer.valueOf(room.getState()) == 1 || Integer.valueOf(room.getState()) == 3) {
                label1.setBackground(new Color(225,245,254));
            }
            if (Integer.valueOf(room.getState()) == 2 || Integer.valueOf(room.getState()) == 4) {
                label1.setBackground(new Color(55,71,79));
            }

            JButton button = new JButton("ODA BILGILERI");
            button.setSize(135, 30);
            button.setLocation(2, 62);
            button.setBackground(new Color(114,131,167));

            button.addActionListener((ActionEvent e) -> {
                roomClick(room);
                dispose();
            });

            panel.add(button);
            panel.add(label1);
            panel.add(label);
            panel.setLayout(null);
            pnlMain.add(panel);
            pnlMain.setLayout(null);
            x = x + 147;

            this.invalidate();
            this.validate();
            this.repaint();

        }

    }

    public void roomRead() {
        try {
            ResultSet rs = db.connect().executeQuery("call roomSelectPro();");
            while (rs.next()) {
                RoomPro rp = new RoomPro();
                rp.setRoom_id(rs.getInt(RoomEnum.room_id + ""));
                rp.setNumber(rs.getString(RoomEnum.number + ""));
                rp.setType_id(rs.getInt(RoomEnum.type_id + ""));
                rp.setType(rs.getString(RoomEnum.type + ""));
                rp.setState(rs.getString(RoomEnum.state + ""));
                rp.setPrice(rs.getFloat(RoomEnum.price + ""));

                roomLs.add(rp);
            }
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("Room Read Error:" + e);
        }
    }

    public void roomClick(RoomPro rm) {
        pnlMain.setVisible(false);
        RoomDesign rd = new RoomDesign(rm);
        rd.setVisible(true);
        roomCreat();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlMain = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnUsers = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        btnPassword = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        btnRooms = new javax.swing.JButton();
        btnHosting = new javax.swing.JButton();
        btnBooking = new javax.swing.JButton();
        btnShowBooking = new javax.swing.JButton();
        btnCustomers = new javax.swing.JButton();
        btnStaff = new javax.swing.JButton();
        btnBills = new javax.swing.JButton();
        btnAddItem = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        btnAddRoom = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kullanıcı Ana Ekranı");
        setLocation(new java.awt.Point(200, 50));

        jPanel1.setLayout(new java.awt.CardLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 600));

        pnlMain.setPreferredSize(new java.awt.Dimension(600, 620));
        pnlMain.setLayout(new java.awt.GridLayout(0, 4, 5, 5));
        jScrollPane1.setViewportView(pnlMain);

        jPanel1.add(jScrollPane1, "card2");

        jLabel1.setBackground(new java.awt.Color(255, 255, 204));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logoSon.png"))); // NOI18N
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });

        btnUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/users.png"))); // NOI18N
        btnUsers.setText("KULLANICILAR");
        btnUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsersActionPerformed(evt);
            }
        });

        btnSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/settings.png"))); // NOI18N
        btnSettings.setText("AYARLAR");
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });

        btnPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/password.png"))); // NOI18N
        btnPassword.setText("SIFRE DEGISTIRME");
        btnPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasswordActionPerformed(evt);
            }
        });

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout.png"))); // NOI18N
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        btnRooms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/room.png"))); // NOI18N
        btnRooms.setText("  ODALAR               ");
        btnRooms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoomsActionPerformed(evt);
            }
        });

        btnHosting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hosts.png"))); // NOI18N
        btnHosting.setText("  KONAKLAYANLAR ");
        btnHosting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHostingActionPerformed(evt);
            }
        });

        btnBooking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/booking.png"))); // NOI18N
        btnBooking.setText(" REZERVASYON YAP");
        btnBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookingActionPerformed(evt);
            }
        });

        btnShowBooking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/showbooking.png"))); // NOI18N
        btnShowBooking.setText("  REZERVASYONLAR");
        btnShowBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowBookingActionPerformed(evt);
            }
        });

        btnCustomers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/customers.png"))); // NOI18N
        btnCustomers.setText("  MUSTERILER       ");
        btnCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomersActionPerformed(evt);
            }
        });

        btnStaff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/staff.png"))); // NOI18N
        btnStaff.setText("  PERSONELLER     ");
        btnStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStaffActionPerformed(evt);
            }
        });

        btnBills.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bills.png"))); // NOI18N
        btnBills.setText("   FATURALAR         ");
        btnBills.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBillsActionPerformed(evt);
            }
        });

        btnAddItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/itemadd.png"))); // NOI18N
        btnAddItem.setText("  ESYA EKLE          ");
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/homepage.png"))); // NOI18N

        btnAddRoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/roomadd.png"))); // NOI18N
        btnAddRoom.setText("  ODA EKLEME       ");
        btnAddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRoomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnShowBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRooms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHosting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCustomers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBills, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddRoom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUsers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSettings)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnHosting, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnShowBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBills, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddItem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRoomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoomsActionPerformed
        jPanel1.removeAll();
        jPanel1.repaint();
        jPanel1.revalidate();
        jPanel1.add(jScrollPane1);
        jPanel1.repaint();
        jPanel1.revalidate();
    }//GEN-LAST:event_btnRoomsActionPerformed

    private void btnHostingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHostingActionPerformed
        showPage(new pnlHosting());
    }//GEN-LAST:event_btnHostingActionPerformed

    private void btnBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookingActionPerformed
        showPage(new pnlBooking());
    }//GEN-LAST:event_btnBookingActionPerformed

    private void btnShowBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowBookingActionPerformed
        showPage(new pnlBooksShow());
    }//GEN-LAST:event_btnShowBookingActionPerformed

    private void btnCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomersActionPerformed
        showPage(new pnlCustomers());
    }//GEN-LAST:event_btnCustomersActionPerformed

    private void btnStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStaffActionPerformed
        showPage(new pnlStaff());
    }//GEN-LAST:event_btnStaffActionPerformed

    private void btnBillsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBillsActionPerformed
        showPage(new pnlBills());
    }//GEN-LAST:event_btnBillsActionPerformed

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        showPage(new pnlTypeItem());
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void btnAddRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRoomActionPerformed
        showPage(new pnlRoomAdd());
    }//GEN-LAST:event_btnAddRoomActionPerformed

    private void btnUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsersActionPerformed

       showPage(new pnlUsers());
    }//GEN-LAST:event_btnUsersActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
 showPage(new pnlUserSetting());
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void btnPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasswordActionPerformed
 showPage(new pnlUserPassword());
    }//GEN-LAST:event_btnPasswordActionPerformed

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered

        jLabel1.setBackground(new Color(255, 255, 124));
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited

       jLabel1.setBackground(new Color(255, 255, 204));
    }//GEN-LAST:event_jLabel1MouseExited

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed

       this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HotelOtomation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HotelOtomation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HotelOtomation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HotelOtomation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new HotelOtomation().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnAddRoom;
    private javax.swing.JButton btnBills;
    private javax.swing.JButton btnBooking;
    private javax.swing.JButton btnCustomers;
    private javax.swing.JButton btnHosting;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPassword;
    private javax.swing.JButton btnRooms;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnShowBooking;
    private javax.swing.JButton btnStaff;
    private javax.swing.JButton btnUsers;
    private javax.swing.JButton jButton13;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlMain;
    // End of variables declaration//GEN-END:variables

   

}
