/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import enums.RoomEnum;
import enums.TypeEnum;
import enums.bookingEnum;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import properties.BookingPro;
import properties.TypePro1;

/**
 *
 * @author Huda
 */
public class pnlBooking extends javax.swing.JPanel {

    /**
     * Creates new form pnlBooking
     */
    public pnlBooking() {
        initComponents();
        odaTiplerGetir();
        rezervasyonKontrol();
        rezerveDegil();
        backGroundColor();
    }

    Db db = new Db("oteldenemeleri", "root", "");
    LoggerClass lg = new LoggerClass();
    ArrayList<BookingPro> bpls = new ArrayList<>();
    ArrayList<TypePro1> tls = new ArrayList<>();
    ArrayList<String> room_id = new ArrayList<>();

    public void backGroundColor() {
        this.setBackground(new Color(119, 119, 119));
        pnlBooking.setBackground(new Color(119, 119, 119));
        pnlSearch.setBackground(new Color(119, 119, 119));
        pnlAvailableRooms.setBackground(new Color(119, 119, 119));
        cmbRoomType.setBackground(new Color(119, 119, 119));
        dcEnterDate.setBackground(new Color(160, 160, 160));
        dcExitDate.setBackground(new Color(160, 160, 160));
        txtPhoneNumber.setBackground(new Color(160, 160, 160));
        btnSearch.setBackground(new Color(75, 75, 75));
        btnOk.setBackground(new Color(75, 75, 75));
        roomTable.setBackground(new Color(160, 160, 160));

    }

    private void rezervasyonKontrol() {
        try {
            PreparedStatement pr = db.preConnect("call bookingsControlPro();");
            int i = pr.executeUpdate();
            if (i > 0) {
               JOptionPane.showMessageDialog(this, "tarihi geçen rezervasyonlar silindi");
            }
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("rezervasyon silme hatasi " + e);
        }
    }

    private void odaTiplerGetir() {
        TypePro1 ftp = new TypePro1();
        ftp.setType_id("");
        ftp.setType("");
        ftp.setPrice("");
        tls.add(ftp);
        try {
            ResultSet rs = db.connect().executeQuery("call typeSelectPro()");
            while (rs.next()) {
                TypePro1 tp = new TypePro1();
                tp.setType_id(rs.getString("" + TypeEnum.type_id));
                tp.setType(rs.getString("" + TypeEnum.type));
                tp.setPrice(rs.getString("" + TypeEnum.price));
                tls.add(tp);
            }
            odaTipleriDoldur();
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("oda tipi getirme hatasi " + ex);
        }
    }

    private void odaTipleriDoldur() {
        DefaultComboBoxModel dcm = new DefaultComboBoxModel();
        for (TypePro1 item : tls) {
            dcm.addElement(item.getType());
        }
        cmbRoomType.setModel(dcm);
    }
    int id = -1;
    String gt = "", ct = "";

    private void rezerveDegil() {
        try {

            room_id.clear();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            gt = "";
            ct = "";
            if (dcEnterDate.getDate() != null && dcExitDate.getDate() != null) {
                gt = df.format(dcEnterDate.getDate());
                ct = df.format(dcExitDate.getDate());
            }
            ResultSet rs = db.connect().executeQuery("Call bookingsSelectPro('','','')");
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("Oda Numarası");
            dtm.addColumn("Tipi");
            dtm.addColumn("Fiyatı");
            while (rs.next()) {
                room_id.add(rs.getString("" + bookingEnum.room_id));
                dtm.addRow(new String[]{rs.getString("" + RoomEnum.number), rs.getString("" + TypeEnum.type), rs.getString("" + TypeEnum.price)});
            }
            roomTable.setModel(dtm);
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("rezervasyon arama hatasi " + ex);
        }
    }

    public void btnSearch() {

        try {
            room_id.clear();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            gt = "";
            ct = "";
            if (dcEnterDate.getDate() != null && dcExitDate.getDate() != null) {
                gt = df.format(dcEnterDate.getDate());
                ct = df.format(dcExitDate.getDate());
            }
            PreparedStatement pr = db.preConnect("Call bookingsSelectPro(?,?,?)");
            pr.setString(1, gt);
            pr.setString(2, ct);
            pr.setString(3, tls.get(cmbRoomType.getSelectedIndex()).getType_id());
            ResultSet rs = pr.executeQuery();
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.addColumn("Oda Numarası");
            dtm.addColumn("Tipi");
            dtm.addColumn("Fiyatı");
            while (rs.next()) {
                room_id.add(rs.getString("" + bookingEnum.room_id));
                dtm.addRow(new String[]{rs.getString("" + RoomEnum.number), rs.getString("" + TypeEnum.type), rs.getString("" + TypeEnum.price)});
            }
            roomTable.setModel(dtm);
        } catch (SQLException ex) {
            lg.loggerFunction(ex);
//            System.err.println("rezervasyon arama hatasi " + ex);
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

        pnlBooking = new javax.swing.JPanel();
        pnlSearch = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbRoomType = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        dcEnterDate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        dcExitDate = new com.toedter.calendar.JDateChooser();
        btnSearch = new javax.swing.JButton();
        pnlAvailableRooms = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtPhoneNumber = new javax.swing.JTextField();
        btnOk = new javax.swing.JButton();

        pnlBooking.setBorder(javax.swing.BorderFactory.createTitledBorder("Rezervasyon İşlemi"));
        pnlBooking.setPreferredSize(new java.awt.Dimension(580, 510));

        pnlSearch.setBorder(javax.swing.BorderFactory.createTitledBorder("Arama"));

        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Oda Türü ");

        cmbRoomType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Giriş Tarihi ");

        dcEnterDate.setDateFormatString("yyyy-MM-dd hh:mm");

        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Çıkış Tarihi");

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(cmbRoomType, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(dcEnterDate, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcExitDate, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSearch)
                    .addGroup(pnlSearchLayout.createSequentialGroup()
                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbRoomType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dcEnterDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dcExitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pnlAvailableRooms.setBorder(javax.swing.BorderFactory.createTitledBorder("Uygun Odalar"));

        roomTable.setModel(new javax.swing.table.DefaultTableModel(
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
        roomTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(roomTable);

        jLabel4.setText("Telefon Numarası ");

        btnOk.setText("Rezerve Et");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAvailableRoomsLayout = new javax.swing.GroupLayout(pnlAvailableRooms);
        pnlAvailableRooms.setLayout(pnlAvailableRoomsLayout);
        pnlAvailableRoomsLayout.setHorizontalGroup(
            pnlAvailableRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAvailableRoomsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAvailableRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlAvailableRoomsLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOk)))
                .addContainerGap())
        );
        pnlAvailableRoomsLayout.setVerticalGroup(
            pnlAvailableRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAvailableRoomsLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlAvailableRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnOk)))
        );

        javax.swing.GroupLayout pnlBookingLayout = new javax.swing.GroupLayout(pnlBooking);
        pnlBooking.setLayout(pnlBookingLayout);
        pnlBookingLayout.setHorizontalGroup(
            pnlBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAvailableRooms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBookingLayout.setVerticalGroup(
            pnlBookingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBookingLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(pnlAvailableRooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBooking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

        btnSearch();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void roomTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomTableMouseClicked
        id = Integer.valueOf(room_id.get(roomTable.getSelectedRow()));
    }//GEN-LAST:event_roomTableMouseClicked

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if (id != -1) {
            if (dcEnterDate.getDate() != null && dcExitDate.getDate() != null) {
                if (!txtPhoneNumber.getText().trim().equals("")) {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        gt = "";
                        ct = "";
                        gt = df.format(dcEnterDate.getDate());
                        ct = df.format(dcExitDate.getDate());
                        PreparedStatement pr = db.preConnect("Call bookingsInsertPro(?,?,?,?)");
                        pr.setInt(1, id);
                        pr.setString(2, txtPhoneNumber.getText());
                        pr.setString(3, gt);
                        pr.setString(4, ct);
                        int i = pr.executeUpdate();
                        if (i > 0) {
                            JOptionPane.showMessageDialog(this, "Rezerve etme işlemi başarılı");
                            btnSearch();
                            id = -1;
                        } else {
                            JOptionPane.showMessageDialog(this, "işlem başarısız!");
                        }
                    } catch (SQLException ex) {
                        lg.loggerFunction(ex);
//                        System.err.println("rezrvasyon ekleme hatasi " + ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "lütfen 'TELEFON NUMARASI' bölümünü doldurunuz!");
                }

            } else {
                JOptionPane.showMessageDialog(this, "lütfen 'TARİH İLE İLGİLİ' bölümleri doldurunuz!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "lütfen tablo üzerinden bir oda seçiniz!");
        }

    }//GEN-LAST:event_btnOkActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOk;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbRoomType;
    private com.toedter.calendar.JDateChooser dcEnterDate;
    private com.toedter.calendar.JDateChooser dcExitDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlAvailableRooms;
    private javax.swing.JPanel pnlBooking;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JTable roomTable;
    private javax.swing.JTextField txtPhoneNumber;
    // End of variables declaration//GEN-END:variables
}
