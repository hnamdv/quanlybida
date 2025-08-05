/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import MODEl.Dichvu;
import UI.Panel.BanBidaPanel;
import UI.Panel.BookingPanel;
import UI.Panel.ChiTietBookingPanel;
import UI.Panel.ChitiethoadonPanel;
import UI.Panel.QuanLyNhanVien;
import UI.Panel.SuaChuaPanel;
import UI.Panel.ThuCong;
import UI.Panel.maqr;
import UI.Panel.quetmaqr;
import UI.Panel.thongke;
import Xauth.phanquyen;
import Xauth.phanquyen.SessionData;
import com.formdev.flatlaf.FlatLightLaf;
import img.text;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Admin
 */
public class nhanvien extends javax.swing.JFrame {

    /**
     * Creates new form nhanvien
     */
    public nhanvien() {
        initComponents();
        setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Giao diện sáng
            // UIManager.setLookAndFeel(new FlatDarkLaf()); // Giao diện tối
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        if (phanquyen.isLogin()) {
            lblchucvu.setText("Chức vụ: " + phanquyen.user.getChucVu()
            );
            lblmnv.setText("Mã NV: " + phanquyen.user.getMaNV());
        }

    }
    private boolean isDarkTheme = false;
private void phanQuyenTheoChucVu() {
    if (!phanquyen.check()) {
      //  .setEnabled(false);
     //  btnNhanVien.setEnabled(false);
      //  btnChiTietHoaDon.setEnabled(false);
      //  btnChiTietBooking.setEnabled(false);
     //   btnSuaChua.setEnabled(false);
      //  btnQR.setEnabled(false);
       // các nút khác nếu cần
    }
}

private void btnChamCongMouseClicked(java.awt.event.MouseEvent evt) {
    // TODO: xử lý khi click chuột vào nút Chấm Công
    card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new quetmaqr());
}
private void btnChamCongActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO: xử lý khi bấm nút Chấm Công
        card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new quetmaqr());
}

private void btnDichVUActionPerformed(java.awt.event.ActionEvent evt) {
  douong form = new douong();
        form.setVisible(true);
}

private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {
    // TODO: xử lý khi click chuột vào nút Menu
           card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new BanBidaPanel());
}

private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO: xử lý khi bấm nút Menu
}

private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO: xử lý khi bấm nút Nhân Viên
         card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new QuanLyNhanVien());
}

private void btnQuenActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO: xử lý khi bấm nút Quên Mật Khẩu
         card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new quenmatkhau());
}

private void btnDangXUatActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO: xử lý khi bấm nút Đăng Xuất
}

private void btnBookingActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO: xử lý khi bấm nút Booking
         card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new BookingPanel());
}

private void btnDoanhThuActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO: xử lý khi bấm nút Doanh Thu
     card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new thongke());
        
}

private void btnChiTietHoaDonActionPerformed(java.awt.event.ActionEvent evt) {
 card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new ChitiethoadonPanel()
        );
}

private void btnChiTietBookingActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO: xử lý khi bấm nút Chi Tiết Booking
     card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new ChiTietBookingPanel());
}

private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO: xử lý khi bấm jButton11
            card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new SuaChuaPanel());
}

private void btnQRActionPerformed(java.awt.event.ActionEvent evt) {
    // TODO: xử lý khi bấm nút QR
        card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new maqr());
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnChamCong = new javax.swing.JButton();
        btnDichVU = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        btnNhanVien = new javax.swing.JButton();
        btnQuen = new javax.swing.JButton();
        btnDangXUat = new javax.swing.JButton();
        btnBooking = new javax.swing.JButton();
        lblchucvu = new javax.swing.JLabel();
        lblmnv = new javax.swing.JLabel();
        btnDoanhThu = new javax.swing.JButton();
        btntheme = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panelSubDichVu = new javax.swing.JPanel();
        btnThucUong = new javax.swing.JButton();
        btnSuaChua = new javax.swing.JButton();
        btnChiTietHoaDon = new javax.swing.JButton();
        btnChiTietBooking = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        btnQR = new javax.swing.JButton();
        btnphanca = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        card.setBackground(new java.awt.Color(255, 255, 255));
        card.setToolTipText("");
        card.setPreferredSize(new java.awt.Dimension(1620, 1080));
        card.setLayout(new java.awt.CardLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Chức vụ:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MSNV:");

        btnChamCong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChamCong.setForeground(new java.awt.Color(51, 51, 51));
        btnChamCong.setText(" Chấm Công");
        btnChamCong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChamCongMouseClicked(evt);
            }
        });
        btnChamCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChamCongActionPerformed(evt);
            }
        });

        btnDichVU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDichVU.setForeground(new java.awt.Color(51, 51, 51));
        btnDichVU.setText("Dịch Vụ");
        btnDichVU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDichVUActionPerformed(evt);
            }
        });

        btnMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMenu.setText("Menu");
        btnMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuMouseClicked(evt);
            }
        });
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        btnNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNhanVien.setText("Nhân Viên");
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });

        btnQuen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnQuen.setText("Đổi mật khẩu");
        btnQuen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuenActionPerformed(evt);
            }
        });

        btnDangXUat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDangXUat.setText("Đăng xuất ");
        btnDangXUat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXUatActionPerformed(evt);
            }
        });

        btnBooking.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBooking.setText("Booking");
        btnBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookingActionPerformed(evt);
            }
        });

        lblchucvu.setForeground(new java.awt.Color(255, 255, 255));
        lblchucvu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblchucvu.setText("jLabel4");

        lblmnv.setForeground(new java.awt.Color(255, 255, 255));
        lblmnv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblmnv.setText("jLabel5");

        btnDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDoanhThu.setText("Doanh Thu");
        btnDoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoanhThuActionPerformed(evt);
            }
        });

        btntheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemeActionPerformed(evt);
            }
        });

        panelSubDichVu.setLayout(new javax.swing.BoxLayout(panelSubDichVu, javax.swing.BoxLayout.Y_AXIS));

        btnThucUong.setText("   - Thức uống");
        panelSubDichVu.add(btnThucUong);

        btnSuaChua.setText("   - Sửa chữa");
        panelSubDichVu.add(btnSuaChua);

        btnChiTietHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChiTietHoaDon.setText("Chi Tiết Hóa Đơn");
        btnChiTietHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietHoaDonActionPerformed(evt);
            }
        });

        btnChiTietBooking.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChiTietBooking.setText("Chi Tiết Booking");
        btnChiTietBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietBookingActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton11.setText("Sửa Chữa");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        btnQR.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnQR.setText("Mã QR ");
        btnQR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQRActionPerformed(evt);
            }
        });

        btnphanca.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnphanca.setText("Phân Ca");
        btnphanca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnphancaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 22, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDangXUat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNhanVien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBooking, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnDichVU, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnChamCong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDoanhThu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnQuen, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btntheme, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnChiTietHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnChiTietBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnphanca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblmnv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblchucvu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelSubDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblchucvu)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lblmnv)
                .addGap(28, 28, 28)
                .addComponent(btnChamCong)
                .addGap(18, 18, 18)
                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDichVU)
                .addGap(18, 18, 18)
                .addComponent(btnBooking)
                .addGap(18, 18, 18)
                .addComponent(btnNhanVien)
                .addGap(18, 18, 18)
                .addComponent(btnDoanhThu)
                .addGap(18, 18, 18)
                .addComponent(btnQuen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnphanca)
                .addGap(18, 18, 18)
                .addComponent(btnChiTietHoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnChiTietBooking)
                .addGap(18, 18, 18)
                .addComponent(jButton11)
                .addGap(18, 18, 18)
                .addComponent(btnQR)
                .addGap(18, 18, 18)
                .addComponent(btntheme, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDangXUat)
                .addGap(39, 39, 39))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 469, Short.MAX_VALUE)
                    .addComponent(panelSubDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 470, Short.MAX_VALUE)))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(card, javax.swing.GroupLayout.DEFAULT_SIZE, 1299, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new maqr());
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        douong form = new douong();
        form.setVisible(true); // Lúc này cửa sổ form sẽ bật lên

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
            // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new quetmaqr());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new QuanLyNhanVien());    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new ChitiethoadonPanel());  
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        phanquyen.clear();

        File f = new File("remember.txt");
        if (f.exists()) {
            f.delete();
        }

        this.dispose();
        new dangnhap().setVisible(true);


    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new phanca());
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnthemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemeActionPerformed
        // TODO add your handling code here:
        try {
            if (isDarkTheme) {
                UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
                isDarkTheme = false;
            } else {
                UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
                isDarkTheme = true;
            }
            SwingUtilities.updateComponentTreeUI(this);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnthemeActionPerformed

    private void btnphancaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnphancaActionPerformed
        // TODO add your handling code here:
          card.revalidate();
        card.repaint();
        card.removeAll();
        card.add(new phanca());
    }//GEN-LAST:event_btnphancaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(nhanvien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(nhanvien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(nhanvien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nhanvien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new nhanvien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBooking;
    private javax.swing.JButton btnChamCong;
    private javax.swing.JButton btnChiTietBooking;
    private javax.swing.JButton btnChiTietHoaDon;
    private javax.swing.JButton btnDangXUat;
    private javax.swing.JButton btnDichVU;
    private javax.swing.JButton btnDoanhThu;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnQR;
    private javax.swing.JButton btnQuen;
    private javax.swing.JButton btnSuaChua;
    private javax.swing.JButton btnThucUong;
    private javax.swing.JButton btnphanca;
    private javax.swing.JButton btntheme;
    private javax.swing.JPanel card;
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblchucvu;
    private javax.swing.JLabel lblmnv;
    private javax.swing.JPanel panelSubDichVu;
    // End of variables declaration//GEN-END:variables
}
