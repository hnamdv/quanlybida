/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Panel;

import DAO.DaoImple.BanbidaDAO;
import DAO.DaoImple.SuachuaDAO;
import MODEl.Suachua;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author HP
 */
public class SuaChuaPanel extends javax.swing.JPanel {

    private JList<String> listBan;

    private DefaultTableModel model;

    /**
     * Creates new form SuaChuaPanel
     */
public SuaChuaPanel() {
    initComponents();
    
    // Khởi tạo danh sách bàn trống
    DefaultListModel<String> modelList = new DefaultListModel<>();
    for (String maBan : new BanbidaDAO().getAllMaBanDangTrong()) {
        modelList.addElement(maBan);
    }
    listBan = new JList<>(modelList);
    listBan.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jPanel2.add(new JScrollPane(listBan));

    // Quan trọng ❗❗ Gán model cho bảng
    model = (DefaultTableModel) tblSuaChua.getModel();

    loadAll();
    loadCbbBanDangSua();
}

    private void loadAll() {
        SuachuaDAO dao = new SuachuaDAO();
        List<Suachua> list = dao.getAllBanDangSua();
        model.setRowCount(0); // Xóa hết dòng cũ
        for (Suachua s : list) {
            model.addRow(new Object[]{
                s.getMaSC(),
                s.getMaBan(),
                s.getMoTaLoi(),
                s.getChiPhi(),
                s.getNgaySua()
            });
        }
    }

    private void filterByDate() {
        if (dateLoc.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.sql.Date ngay = new java.sql.Date(dateLoc.getDate().getTime());
        SuachuaDAO dao = new SuachuaDAO();
        List<Suachua> list = dao.getByNgaySua(ngay);

        model.setRowCount(0); // Xóa bảng cũ

        for (Suachua s : list) {
            model.addRow(new Object[]{
                s.getMaSC(), s.getMaBan(), s.getMoTaLoi(),
                s.getChiPhi(), s.getNgaySua()
            });
        }
    }
private void openThemSuaChuaDialog() {
    int result = JOptionPane.showConfirmDialog(
        this,
        "Bạn có chắc chắn muốn thêm sửa chữa?",
        "Xác nhận",
        JOptionPane.YES_NO_OPTION
    );

    if (result == JOptionPane.YES_OPTION) {
        try {
            String maSC = "SC" + System.currentTimeMillis();
            String maBan = txtMaBan.getText().trim(); // ✅ Lấy từ textfield
            String moTa = txtMoTa.getText();
            double chiPhi = Double.parseDouble(txtChiPhi.getText());
            java.sql.Date ngaySua = new java.sql.Date(dcNgaySua.getDate().getTime());

            if (maBan.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã bàn!");
                return;
            }

            // ✅ Tạo sửa chữa cho 1 bàn
            Suachua sc = new Suachua(maSC, maBan, moTa, chiPhi, ngaySua);
            System.out.println("Insert sửa chữa: " + sc.getMaSC() + " - " + maBan + " - " + sc.getMoTaLoi());

            // ✅ Thêm vào DB
            SuachuaDAO dao = new SuachuaDAO();
            dao.insert(sc);  // ✅ Chỉ insert 1 dòng

            dao.capNhatTinhTrang(maBan, "BaoTri");  // ✅ Cập nhật tình trạng

            loadAll();
            JOptionPane.showMessageDialog(this, "✅ Thêm sửa chữa thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

    private void loadCbbBanDangSua() {
        jComboBox1.removeAllItems(); // Xóa item cũ
        SuachuaDAO dao = new SuachuaDAO();
        List<String> danhSach = dao.getDanhSachMaBanDangSua();

        for (String maBan : danhSach) {
            jComboBox1.addItem(maBan);
        }
    }
public Suachua getForm() {
   Suachua sc = new Suachua();
sc.setMaBan(txtMaBan.getText().trim());
sc.setMoTaLoi(txtMoTa.getText().trim());

// Chi phí: ép kiểu từ String sang double
try {
    sc.setChiPhi(Double.parseDouble(txtChiPhi.getText().trim()));
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "❌ Chi phí phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    return null;
}

// Ngày sửa: từ util.Date -> sql.Date
java.util.Date utilDate = dcNgaySua.getDate();
if (utilDate == null) {
    JOptionPane.showMessageDialog(this, "❌ Vui lòng chọn ngày sửa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
    return null;
}
sc.setNgaySua(new java.sql.Date(utilDate.getTime()));

    return sc;
}

   
public void setForm(Suachua sc) {
    txtMaBan.setText(sc.getMaBan());
    txtMoTa.setText(sc.getMoTaLoi());
    txtChiPhi.setText(String.valueOf(sc.getChiPhi()));
    dcNgaySua.setDate(sc.getNgaySua());
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblSuaChua = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dateLoc = new com.toedter.calendar.JDateChooser();
        btnLoc = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtMaBan = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dcNgaySua = new com.toedter.calendar.JDateChooser();
        txtMoTa = new javax.swing.JTextField();
        txtChiPhi = new javax.swing.JTextField();
        btnThemSuaChua = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1620, 1080));
        setRequestFocusEnabled(false);

        tblSuaChua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblSuaChua.setForeground(new java.awt.Color(0, 102, 102));
        tblSuaChua.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã SC", "Mã Bàn", "Mô Tả Lỗi", "Chi Phí", "Ngày Sửa"
            }
        ));
        tblSuaChua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSuaChuaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSuaChua);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Ngày sửa:");

        btnLoc.setBackground(new java.awt.Color(0, 102, 102));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(255, 255, 255));
        btnLoc.setText("Lọc theo ngày");
        btnLoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLocMouseClicked(evt);
            }
        });
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnLoc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtMaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaBanActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Mã bàn:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Mô tả lỗi:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Chi phí:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("Ngày sửa:");

        btnThemSuaChua.setBackground(new java.awt.Color(0, 102, 102));
        btnThemSuaChua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSuaChua.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSuaChua.setText("Xác nhận");
        btnThemSuaChua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThemSuaChuaMouseClicked(evt);
            }
        });
        btnThemSuaChua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSuaChuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaBan)
                    .addComponent(txtMoTa)
                    .addComponent(txtChiPhi)
                    .addComponent(dcNgaySua, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnThemSuaChua, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMoTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtChiPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(dcNgaySua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnThemSuaChua)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sửa xong");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(820, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(647, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLocMouseClicked
        // TODO add your handling code here:
        filterByDate();
    }//GEN-LAST:event_btnLocMouseClicked

    private void btnThemSuaChuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemSuaChuaMouseClicked
        // TODO add your handling code here:
        openThemSuaChuaDialog();
    }//GEN-LAST:event_btnThemSuaChuaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int row = tblSuaChua.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "❌ Vui lòng chọn dòng cần đánh dấu đã sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String maBan = tblSuaChua.getValueAt(row, 1).toString();  // Cột mã bàn
    int xacNhan = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn đã sửa xong bàn " + maBan + "?",
            "Xác nhận", JOptionPane.YES_NO_OPTION);

    if (xacNhan == JOptionPane.YES_OPTION) {
        new SuachuaDAO().capNhatTinhTrang(maBan, "Trong");
        JOptionPane.showMessageDialog(this, "✅ Đã cập nhật trạng thái bàn về 'Trong'!");
        loadAll(); // Refresh bảng
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblSuaChuaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSuaChuaMouseClicked
        // TODO add your handling code here:
        SuachuaDAO dao = new SuachuaDAO();
            int row = tblSuaChua.getSelectedRow();
        if (row >= 0) {
            String maBan = tblSuaChua.getValueAt(row, 0).toString(); // cột mã bàn
            Suachua sc = dao.findById(maBan);
            if (sc != null) {
                setForm(sc);
            }
        }
    
    }//GEN-LAST:event_tblSuaChuaMouseClicked

    private void btnThemSuaChuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSuaChuaActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnThemSuaChuaActionPerformed

    private void txtMaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaBanActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnLocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnThemSuaChua;
    private com.toedter.calendar.JDateChooser dateLoc;
    private com.toedter.calendar.JDateChooser dcNgaySua;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSuaChua;
    private javax.swing.JTextField txtChiPhi;
    private javax.swing.JTextField txtMaBan;
    private javax.swing.JTextField txtMoTa;
    // End of variables declaration//GEN-END:variables
}
