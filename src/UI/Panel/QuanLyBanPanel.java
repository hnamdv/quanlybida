package UI.Panel;

import DAO.DaoImple.BanbidaDAO;
import DAO.DaoImple.LoaibanDAO;
import MODEl.Banbida;
import MODEl.Loaiban;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class QuanLyBanPanel extends javax.swing.JPanel {

    private Map<String, String> tenToMaLoaiMap = new HashMap<>();
    private DefaultTableModel tableModel;
    private BanbidaDAO dao = new BanbidaDAO();
    private List<Banbida> allBan; // Lưu toàn bộ bàn để lọc

    /**
     * Creates new form QuanLyBanPanel
     */
    public QuanLyBanPanel() {
        initComponents();
        tableModel = (DefaultTableModel) tblBan.getModel();
        initLoaiBanCBB();
        allBan = new BanbidaDAO().getAll(); // Lấy toàn bộ bàn một lần
        loadTable(allBan); // Hiển thị ban đầu
        addSearchListeners();
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0); // Clear table
        List<Banbida> list = dao.getAll();

        for (Banbida ban : list) {
            tableModel.addRow(new Object[]{
                ban.getMaBan(),
                ban.getTenBan(),
                ban.getMaLoaiBan(),
                ban.getTinhTrang(),
                ban.getGiaTheoGio(),
                ban.getTuoiBan(),
                ban.getGhiChu()
            });
        }
    }

    private void themBan() {
        String ma = txtmaBan.getText().trim();
        String ten = txttenBan.getText().trim();
        String loai = txtloaiBan.getText().trim();
        String tinhtrang = txttinhTrang.getText().trim();
        String gia = txtgiaTheogio.getText().trim();
        String tuoi = txttuoiBan.getText().trim();
        String ghiChu = txtghiChu.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống mã hoặc tên bàn!");
            return;
        }

        try {
            Banbida ban = new Banbida(ma, ten, loai, tinhtrang,
                    Double.parseDouble(gia),
                    Integer.parseInt(tuoi),
                    ghiChu);

            if (dao.insert(ban)) {
                JOptionPane.showMessageDialog(this, "Thêm bàn thành công!");
                loadDataToTable();
                lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm bàn thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá theo giờ và tuổi bàn phải là số!");
        }
    }

    private void suaBan() {
        int row = tblBan.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn 1 hàng để sửa!");
            return;
        }

        try {
            Banbida ban = new Banbida(
                    txtmaBan.getText(),
                    txttenBan.getText(),
                    txtloaiBan.getText(),
                    txttinhTrang.getText(),
                    Double.parseDouble(txtgiaTheogio.getText()),
                    Integer.parseInt(txttuoiBan.getText()),
                    txtghiChu.getText()
            );

            if (dao.update(ban)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadDataToTable();
                lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá theo giờ và tuổi bàn phải là số!");
        }
    }

    private void xoaBan() {
        int row = tblBan.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn 1 hàng để xoá!");
            return;
        }

        String maBan = tblBan.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xoá bàn " + maBan + " không?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(maBan)) {
                JOptionPane.showMessageDialog(this, "Xoá thành công!");
                loadDataToTable();
                lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xoá thất bại!");
            }
        }
    }

// Load combobox loại bàn
    private void initLoaiBanCBB() {
        cbbtimLoai.removeAllItems();
        cbbtimLoai.addItem("Tất cả");
        tenToMaLoaiMap.clear();

        Map<String, Loaiban> loaiBanMap = LoaibanDAO.getMapLoaiBan();
        for (Loaiban lb : loaiBanMap.values()) {
            cbbtimLoai.addItem(lb.getTenLoai()); // Hiển thị tên loại
            tenToMaLoaiMap.put(lb.getTenLoai(), lb.getMaLoaiBan()); // Lưu ánh xạ
        }
    }

// Thêm listener cho tìm kiếm tự động
    private void addSearchListeners() {
        cbbtimLoai.addActionListener(e -> timKiemAuto());

        txttimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timKiemAuto();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timKiemAuto();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                timKiemAuto();
            }
        });
    }

// Lọc dữ liệu
    private void timKiemAuto() {
        String keyword = txttimKiem.getText().trim().toLowerCase();
        String selectedTenLoai = (String) cbbtimLoai.getSelectedItem();

        String maLoai = "";
        if (selectedTenLoai != null && !"Tất cả".equals(selectedTenLoai)) {
            maLoai = tenToMaLoaiMap.getOrDefault(selectedTenLoai, "");
        }

        List<Banbida> filteredList = new ArrayList<>();
        for (Banbida b : allBan) {
            boolean matchMa = keyword.isEmpty() || b.getMaBan().toLowerCase().contains(keyword);
            boolean matchLoai = "Tất cả".equals(selectedTenLoai) || b.getMaLoaiBan().equalsIgnoreCase(maLoai);
            if (matchMa && matchLoai) {
                filteredList.add(b);
            }
        }
        loadTable(filteredList);
    }

// Load danh sách bàn vào bảng
    private void loadTable(List<Banbida> list) {
        tableModel.setRowCount(0);
        for (Banbida b : list) {
            tableModel.addRow(new Object[]{
                b.getMaBan(),
                b.getTenBan(),
                b.getMaLoaiBan(),
                b.getTinhTrang(),
                b.getGiaTheoGio(),
                b.getTuoiBan(),
                b.getGhiChu()
            });
        }
    }

    private void lamMoiForm() {
        txtmaBan.setText("");
        txttenBan.setText("");
        txtloaiBan.setText("");
        txttinhTrang.setText("");
        txtgiaTheogio.setText("");
        txttuoiBan.setText("");
        txtghiChu.setText("");
        tblBan.clearSelection();
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
        tblBan = new javax.swing.JTable();
        btnthem = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btnsua = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtmaBan = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txttenBan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txttinhTrang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtgiaTheogio = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txttuoiBan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtghiChu = new javax.swing.JTextField();
        txttimKiem = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cbbtimLoai = new javax.swing.JComboBox<>();
        txtloaiBan = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1620, 1080));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(0, 102, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(1620, 1080));

        tblBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã bàn", "Tên bàn", "Loại bàn", "Tình trạng", "Giá theo giờ ", "Tuổi bàn", "Ghi chú"
            }
        ));
        tblBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBan);

        btnthem.setBackground(new java.awt.Color(0, 102, 102));
        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnthem.setForeground(new java.awt.Color(255, 255, 255));
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnxoa.setBackground(new java.awt.Color(0, 102, 102));
        btnxoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnxoa.setForeground(new java.awt.Color(255, 255, 255));
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btnsua.setBackground(new java.awt.Color(0, 102, 102));
        btnsua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnsua.setForeground(new java.awt.Color(255, 255, 255));
        btnsua.setText("Sửa ");
        btnsua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsuaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Mã bàn");

        txtmaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmaBanActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Tên bàn");

        txttenBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttenBanActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Loại bàn");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("Tình trạng");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Giá theo giờ");

        txtgiaTheogio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtgiaTheogioActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Tuổi bàn");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("Ghi chú");

        txttimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttimKiemActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setText("Tìm kiếm");

        cbbtimLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbtimLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbtimLoaiActionPerformed(evt);
            }
        });

        txtloaiBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtloaiBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(txtmaBan, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(txttenBan)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(txttinhTrang)
                            .addComponent(jLabel6)
                            .addComponent(txtgiaTheogio)
                            .addComponent(jLabel7)
                            .addComponent(txttuoiBan)
                            .addComponent(jLabel8)
                            .addComponent(txtghiChu)
                            .addComponent(txtloaiBan)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txttimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbtimLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 216, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnthem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnxoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnsua)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbtimLoai, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(txttimKiem)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(51, 51, 51)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtmaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)
                        .addComponent(txtloaiBan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtgiaTheogio, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txttuoiBan, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtghiChu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem)
                    .addComponent(btnxoa)
                    .addComponent(btnsua))
                .addGap(0, 314, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblBanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBanMouseClicked
        // TODO add your handling code here:
        int row = tblBan.getSelectedRow();
        if (row != -1) {
            txtmaBan.setText(tblBan.getValueAt(row, 0).toString());
            txttenBan.setText(tblBan.getValueAt(row, 1).toString());
            txtloaiBan.setText(tblBan.getValueAt(row, 2).toString());
            txttinhTrang.setText(tblBan.getValueAt(row, 3).toString());
            txtgiaTheogio.setText(tblBan.getValueAt(row, 4).toString());
            txttuoiBan.setText(tblBan.getValueAt(row, 5).toString());
            txtghiChu.setText(tblBan.getValueAt(row, 6).toString());
        }

    }//GEN-LAST:event_tblBanMouseClicked

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        // TODO add your handling code here:
        themBan();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        // TODO add your handling code here:
        xoaBan();
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnsuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsuaActionPerformed
        // TODO add your handling code here:
        suaBan();
    }//GEN-LAST:event_btnsuaActionPerformed

    private void txtmaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmaBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmaBanActionPerformed

    private void txttenBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttenBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttenBanActionPerformed

    private void txtgiaTheogioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtgiaTheogioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtgiaTheogioActionPerformed

    private void cbbtimLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbtimLoaiActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbbtimLoaiActionPerformed

    private void txttimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttimKiemActionPerformed

    private void txtloaiBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtloaiBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtloaiBanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnsua;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cbbtimLoai;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblBan;
    private javax.swing.JTextField txtghiChu;
    private javax.swing.JTextField txtgiaTheogio;
    private javax.swing.JTextField txtloaiBan;
    private javax.swing.JTextField txtmaBan;
    private javax.swing.JTextField txttenBan;
    private javax.swing.JTextField txttimKiem;
    private javax.swing.JTextField txttinhTrang;
    private javax.swing.JTextField txttuoiBan;
    // End of variables declaration//GEN-END:variables
}
