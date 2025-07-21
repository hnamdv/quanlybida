/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Panel;

import DAO.DaoImple.BanbidaDAO;
import DAO.DaoImple.BookingDAO;
import DAO.DaoImple.DichVuDAO;
import DAO.DaoImple.HoaDonDAO;
import DAO.DaoImple.LoaibanDAO;
import MODEl.Banbida;
import MODEl.Booking;
import MODEl.Dichvu;
import MODEl.Hoadon;
import MODEl.Loaiban;
import Xauth.phanquyen;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;

/**
 *
 * @author HP
 */
public class BanBidaPanel extends javax.swing.JPanel {

    private Hoadon hoaDonTamThoi = null;
    private String currentMaBan = null;

    /**
     * Creates new form QLban
     */
    public BanBidaPanel() {
        initComponents();
        loadDanhSachBan();
        loadDichVuVaoComboBox();
    }

    ///
private void loadDanhSachBan() {
        // Set layout chuẩn (GridLayout sẽ wrap tốt hơn trong ScrollPane so với FlowLayout)
        Pn3bang.setLayout(new GridLayout(0, 4, 10, 10));
        Pn3bangVIP.setLayout(new GridLayout(0, 4, 10, 10));
        Pnlo.setLayout(new GridLayout(0, 4, 10, 10));
        PnloVIP.setLayout(new GridLayout(0, 4, 10, 10));

        // Xóa cũ
        Pn3bang.removeAll();
        Pn3bangVIP.removeAll();
        Pnlo.removeAll();
        PnloVIP.removeAll();

        try {
            BanbidaDAO banDAO = new BanbidaDAO();
            LoaibanDAO loaibanDAO = new LoaibanDAO();
            BookingDAO bookingDAO = new BookingDAO();  // đặt ở đây 1 lần, dùng lại

            List<Banbida> danhSachBan = banDAO.getAll();
            Map<String, Loaiban> loaibanMap = loaibanDAO.getMapLoaiBan();

            for (Banbida ban : danhSachBan) {
                String maBan = ban.getMaBan();
                String tenBan = ban.getTenBan();
                String maLoaiBan = ban.getMaLoaiBan();
                String tinhTrang = ban.getTinhTrang();
                double giaTheoGio = ban.getGiaTheoGio();
                int tuoiBan = ban.getTuoiBan();
                String ghiChu = ban.getGhiChu();

                // Tên loại từ map
                String tenLoai = "";
                if (loaibanMap.containsKey(maLoaiBan)) {
                    tenLoai = loaibanMap.get(maLoaiBan).getTenLoai().toLowerCase();
                }

                // Tạo nút bàn
                JButton btnBan = new JButton(tenBan);
                btnBan.setPreferredSize(new Dimension(100, 80));

                // Kiểm tra nếu bàn đã được đặt trước (trạng thái vẫn là "Trong" nhưng đã có booking)
                Booking thongTinBooking = bookingDAO.getBookingGanNhat(maBan);

                boolean daDatTruoc = false;
                if (thongTinBooking != null && thongTinBooking.getTrangThai().equals("ChuaNhan")) {
                    daDatTruoc = true;
                }

                // Màu theo tình trạng + đặt trước
                if ("BaoTri".equals(tinhTrang)) {
                    btnBan.setBackground(new Color(153, 153, 153));
                    btnBan.setForeground(Color.WHITE);
                    btnBan.setToolTipText("Bàn đang bảo trì");
                } else if (daDatTruoc) {
                    btnBan.setBackground(Color.ORANGE);
                    btnBan.setForeground(Color.BLACK);
                    btnBan.setToolTipText("<html><b>Đã đặt trước</b><br>"
                            + "Khách: " + thongTinBooking.getTenKhach() + "<br>"
                            + "SĐT: " + thongTinBooking.getSdt() + "<br>"
                            + "Nhận lúc: " + thongTinBooking.getGioNhan() + "</html>");
                } else {
                    switch (tinhTrang) {
                        case "DangSuDung" -> {
                            btnBan.setBackground(new Color(0, 255, 0));
                            btnBan.setForeground(Color.WHITE);
                            btnBan.setToolTipText("Đang sử dụng");
                        }
                        case "Trong" -> {
                            btnBan.setBackground(Color.WHITE);
                            btnBan.setForeground(Color.BLACK);
                            btnBan.setToolTipText("Bàn trống");
                        }
                        default -> {
                            btnBan.setBackground(Color.LIGHT_GRAY);
                            btnBan.setForeground(Color.BLACK);
                            btnBan.setToolTipText("Không rõ trạng thái");
                        }
                    }
                }

                // Gắn sự kiện click
                btnBan.addActionListener(e -> chonBan(maBan, tenBan, maLoaiBan, tinhTrang, giaTheoGio, tuoiBan, ghiChu));

                // Phân loại panel
                if (tenLoai.contains("lỗ") && tenLoai.contains("vip")) {
                    PnloVIP.add(btnBan);
                } else if (tenLoai.contains("3 băng") && tenLoai.contains("vip")) {
                    Pn3bangVIP.add(btnBan);
                } else if (tenLoai.contains("3 băng")) {
                    Pn3bang.add(btnBan);
                } else if (tenLoai.contains("vip")) {
                    Pn3bangVIP.add(btnBan);
                } else {
                    Pnlo.add(btnBan);
                }
            }

            // Refresh panels
            Pnlo.revalidate();
            Pnlo.repaint();
            Pn3bang.revalidate();
            Pn3bang.repaint();
            PnloVIP.revalidate();
            PnloVIP.repaint();
            Pn3bangVIP.revalidate();
            Pn3bangVIP.repaint();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách bàn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

// Trong phương thức generateNewHoaDonId() của bạn:
    private String generateNewHoaDonId() {
        // Cách 1: Thêm một số ngẫu nhiên vào sau timestamp (ít khả năng trùng hơn)
        // Cẩn thận với độ dài ID nếu cột MaHD có giới hạn chiều dài
        // return "HD" + System.currentTimeMillis() + (new Random().nextInt(1000));

        // Cách 2: Sử dụng UUID (Universally Unique Identifier) - đây là cách mạnh mẽ nhất để đảm bảo tính duy nhất
        return "HD" + java.util.UUID.randomUUID().toString().replace("-", ""); // Bỏ dấu gạch ngang cho ngắn gọn hơn
    }

    private void chonBan(String maBan, String tenBan, String maLoaiBan, String tinhTrang, double giaTheoGio, int tuoiBan, String ghiChu) {
        this.currentMaBan = maBan;
        jTabbedPane1.setSelectedIndex(1); // chuyển tab

        jLabel10.setText(tenBan);
        jLabel30.setText(tinhTrang);
        jTextField4.setText(ghiChu != null ? ghiChu : "");

        LoaibanDAO loaibanDAO = new LoaibanDAO();
        String tenLoai = loaibanDAO.getTenLoaiByMa(maLoaiBan);
        jLabel28.setText(tenLoai != null ? tenLoai : "Không rõ");

        // Thông tin popup
        JOptionPane.showMessageDialog(this,
                "📌 Tên bàn: " + tenBan
                + "\n🔸 Loại bàn: " + tenLoai
                + "\n📍 Tình trạng: " + tinhTrang
                + "\n📝 Ghi chú: " + (ghiChu == null ? "Không" : ghiChu),
                "Thông tin bàn", JOptionPane.INFORMATION_MESSAGE
        );

        HoaDonDAO hdDAO = new HoaDonDAO();
        Hoadon hd = hdDAO.getHoaDonDangMoByBan(maBan);

        if (tinhTrang.equalsIgnoreCase("Trong") && hd == null) {
            // Khi bàn trống, hãy tạo một mã hóa đơn mới và hiển thị nó
            // Đây là điểm mấu chốt để fix lỗi "mã hóa đơn sai"
            String newMaHD = generateNewHoaDonId();
            jLabel7.setText(newMaHD); // Hiển thị mã hóa đơn mới

            jTextField1.setText("");
            jTextField2.setText("");
            jLabel17.setText("0.0");
            jLabel21.setText("0.0");
            jLabel19.setText("0.0");
            jTextField3.setText("0");
            jLabel31.setText(String.valueOf(giaTheoGio)); // Hiển thị giá theo giờ của bàn trống

            jButton1.setEnabled(true);
            jButton2.setEnabled(false);

        } else if (hd != null) {
            // Bàn đang sử dụng hoặc có hóa đơn mở
            jLabel7.setText(hd.getMaHD());
            jTextField1.setText(hd.getThoiGianBD() != null ? hd.getThoiGianBD().toString() : "");
            jTextField2.setText(hd.getThoiGianKT() != null ? hd.getThoiGianKT().toString() : "");
            jLabel17.setText(String.valueOf(hd.getTienGio()));
            jLabel21.setText(String.valueOf(hd.getTienDV()));
            jLabel19.setText(String.valueOf(hd.getTongTien()));
            jTextField3.setText(String.valueOf((int) hd.getGiamGia()));
            jLabel31.setText(String.valueOf(giaTheoGio)); // Lấy giá theo giờ của bàn
            jTextField4.setText(hd.getGhiChu());

            // Gán hóa đơn đang mở vào hoaDonTamThoi để có thể kết thúc hoặc thêm dịch vụ
            this.hoaDonTamThoi = hd;

            jButton1.setEnabled(false); // Không được bắt đầu lại

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Bàn đang sử dụng.\nBạn có muốn kết thúc không?",
                    "Xác nhận kết thúc",
                    JOptionPane.YES_NO_OPTION
            );

            // Nếu người dùng chọn CÓ, cho phép nút kết thúc
            jButton2.setEnabled(result == JOptionPane.YES_OPTION);
            // Nếu người dùng chọn KHÔNG, tắt luôn nút kết thúc
            if (result == JOptionPane.NO_OPTION) {
                jButton2.setEnabled(false);
            }

        } else {
            // Các trạng thái khác (Bảo trì, Hỏng,...)
            JOptionPane.showMessageDialog(this,
                    "Không thao tác được với trạng thái bàn: " + tinhTrang,
                    "Lỗi", JOptionPane.WARNING_MESSAGE
            );
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
        }
    }

    private void ketThucChoi() {
        if (hoaDonTamThoi == null) {
            JOptionPane.showMessageDialog(this, "❌ Chưa có hóa đơn nào được bắt đầu cho bàn này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp thoiGianKT = new Timestamp(System.currentTimeMillis());
        Timestamp thoiGianBD = hoaDonTamThoi.getThoiGianBD();

        // Đảm bảo thoiGianBD không null
        if (thoiGianBD == null) {
            JOptionPane.showMessageDialog(this, "Thời gian bắt đầu không hợp lệ trong hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        long millis = thoiGianKT.getTime() - thoiGianBD.getTime();
        double gio = millis / (1000.0 * 60 * 60);

        double giaTheoGio = new BanbidaDAO().getGiaTheoMaBan(currentMaBan);
        double tienGio = gio * giaTheoGio;

        double tienDV = parseDoubleSafely(jLabel21.getText());
        double giamGia = parseDoubleSafely(jTextField3.getText());
        double tongTien = tienGio + tienDV - giamGia;

        hoaDonTamThoi.setThoiGianKT(thoiGianKT);
        hoaDonTamThoi.setTienGio(tienGio);
        hoaDonTamThoi.setTongTien(tongTien);

        // Cập nhật lại các trường hiển thị trên UI sau khi tính toán
        jTextField1.setText(hoaDonTamThoi.getThoiGianBD().toString()); // Cập nhật lại thời gian bắt đầu
        jTextField2.setText(hoaDonTamThoi.getThoiGianKT().toString()); // Hiển thị thời gian kết thúc
        jLabel17.setText(String.format("%.2f", tienGio)); // Hiển thị tiền giờ
        jLabel19.setText(String.format("%.2f", tongTien)); // Hiển thị tổng tiền

        JOptionPane.showMessageDialog(this, "⏹️ Đã kết thúc chơi bàn " + currentMaBan + ". Tổng tiền tạm tính: " + String.format("%.2f", tongTien));
        jButton2.setEnabled(false); // tắt nút kết thúc
    }

    private void batDauChoi() {
        if (currentMaBan == null || !jLabel7.getText().trim().startsWith("HD")) {
            JOptionPane.showMessageDialog(this, "❌ Chưa chọn bàn hoặc mã hóa đơn sai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maHD = jLabel7.getText().trim();
        if (!maHD.startsWith("HD")) { // Kiểm tra lại một lần nữa cho chắc
            JOptionPane.showMessageDialog(this, "Mã hóa đơn không hợp lệ! Vui lòng chọn lại bàn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());

        double tienGio = 0;
        double giamGia = parseDoubleSafely(jTextField3.getText());
        double tienDV = parseDoubleSafely(jLabel21.getText());
        double tongTien = 0; // chưa tính đến khi kết thúc

        hoaDonTamThoi = new Hoadon(
                maHD,
                phanquyen.user.getMaNV(),
                currentMaBan,
                now,
                null,
                tongTien,
                "DangMo", // Trạng thái "Đang mở"
                new java.sql.Date(now.getTime()),
                tienGio,
                giamGia,
                tienDV,
                jTextField4.getText()
        );
        // Cần lưu hóa đơn này vào DB ngay khi bắt đầu
        // để nếu ứng dụng bị đóng đột ngột, dữ liệu không bị mất
        HoaDonDAO hdDAO = new HoaDonDAO();
        try {
            hdDAO.insert(hoaDonTamThoi);
            // Cập nhật trạng thái bàn sang "Đang sử dụng"
            new BanbidaDAO().capNhatTinhTrang(currentMaBan, "DangSuDung");
            JOptionPane.showMessageDialog(this, "▶️ Đã bắt đầu tính giờ bàn " + currentMaBan);
            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
            loadDanhSachBan(); // Cập nhật lại trạng thái màu sắc của bàn trên giao diện
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi bắt đầu hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            hoaDonTamThoi = null; // Đặt lại để tránh lỗi tiếp theo
        }

    }

    private void thanhToan() {
        if (hoaDonTamThoi == null || hoaDonTamThoi.getThoiGianKT() == null) {
            JOptionPane.showMessageDialog(this, "❌ Bạn chưa kết thúc chơi để thanh toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Sử dụng giá trị đã tính từ hoaDonTamThoi
        double tienDV = parseDoubleSafely(jLabel21.getText()); // Vẫn lấy từ UI nếu có thể chỉnh sửa DV
        double giamGia = parseDoubleSafely(jTextField3.getText()); // Vẫn lấy từ UI nếu có thể chỉnh sửa giảm giá
        double tienGio = hoaDonTamThoi.getTienGio();

        double tongTien = tienGio + tienDV - giamGia; // Tính toán lại tổng cuối cùng

        hoaDonTamThoi.setTienDV(tienDV);
        hoaDonTamThoi.setGiamGia(giamGia);
        hoaDonTamThoi.setTongTien(tongTien);
        hoaDonTamThoi.setGhiChu(jTextField4.getText());
        hoaDonTamThoi.setTrangThai("DaThanhToan"); // Đặt trạng thái đã thanh toán

        // Lưu DB
        HoaDonDAO hdDAO = new HoaDonDAO();
        BanbidaDAO banDAO = new BanbidaDAO();
        try {
            hdDAO.update(hoaDonTamThoi); // Cập nhật hóa đơn
            banDAO.capNhatTinhTrang(hoaDonTamThoi.getMaBan(), "Trong"); // Cập nhật trạng thái bàn

            JOptionPane.showMessageDialog(this, "💵 Thanh toán thành công! Tổng tiền: " + String.format("%.2f", tongTien));

            // Reset lại trạng thái UI
            hoaDonTamThoi = null;
            currentMaBan = null; // Đặt lại currentMaBan
            jLabel7.setText(""); // Xóa mã hóa đơn
            jTextField1.setText("");
            jTextField2.setText("");
            jLabel17.setText("0.0");
            jLabel21.setText("0.0");
            jLabel19.setText("0.0");
            jTextField3.setText("0");
            jLabel31.setText("0.0");
            jTextField4.setText("");

            jButton1.setEnabled(true);
            jButton2.setEnabled(false);
            loadDanhSachBan(); // Tải lại danh sách bàn để cập nhật trạng thái
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
// Hàm phụ để parse Double an toàn

    private double parseDoubleSafely(String input) {
        try {
            return Double.parseDouble(input.trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    ///
private void loadDichVuVaoComboBox() {
        cbb.removeAllItems();
        DichVuDAO dao = new DichVuDAO();
        try {
            List<Dichvu> dsTenDV = dao.getAll();
            for (Dichvu dv : dsTenDV) {
                cbb.addItem(dv.getTenDV());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi tải danh sách dịch vụ: " + e.getMessage());
        }
    }

    ///
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Pn3bangVIP = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        PnloVIP = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Pn3bang = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Pnlo = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbb = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton15 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1620, 1080));

        jPanel1.setPreferredSize(new java.awt.Dimension(1620, 1080));

        Pn3bangVIP.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("Bàn ba băng V.I.P:");

        javax.swing.GroupLayout Pn3bangVIPLayout = new javax.swing.GroupLayout(Pn3bangVIP);
        Pn3bangVIP.setLayout(Pn3bangVIPLayout);
        Pn3bangVIPLayout.setHorizontalGroup(
            Pn3bangVIPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pn3bangVIPLayout.createSequentialGroup()
                .addComponent(jLabel26)
                .addGap(0, 573, Short.MAX_VALUE))
        );
        Pn3bangVIPLayout.setVerticalGroup(
            Pn3bangVIPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pn3bangVIPLayout.createSequentialGroup()
                .addComponent(jLabel26)
                .addGap(0, 690, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(Pn3bangVIP);

        PnloVIP.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("Bàn lỗ V.I.P:");

        javax.swing.GroupLayout PnloVIPLayout = new javax.swing.GroupLayout(PnloVIP);
        PnloVIP.setLayout(PnloVIPLayout);
        PnloVIPLayout.setHorizontalGroup(
            PnloVIPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnloVIPLayout.createSequentialGroup()
                .addComponent(jLabel23)
                .addGap(0, 554, Short.MAX_VALUE))
        );
        PnloVIPLayout.setVerticalGroup(
            PnloVIPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnloVIPLayout.createSequentialGroup()
                .addComponent(jLabel23)
                .addGap(0, 260, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(PnloVIP);

        Pn3bang.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("Bàn ba băng thường:");

        javax.swing.GroupLayout Pn3bangLayout = new javax.swing.GroupLayout(Pn3bang);
        Pn3bang.setLayout(Pn3bangLayout);
        Pn3bangLayout.setHorizontalGroup(
            Pn3bangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pn3bangLayout.createSequentialGroup()
                .addComponent(jLabel25)
                .addGap(0, 691, Short.MAX_VALUE))
        );
        Pn3bangLayout.setVerticalGroup(
            Pn3bangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pn3bangLayout.createSequentialGroup()
                .addComponent(jLabel25)
                .addGap(0, 301, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(Pn3bang);

        Pnlo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Bàn lỗ thường:");

        javax.swing.GroupLayout PnloLayout = new javax.swing.GroupLayout(Pnlo);
        Pnlo.setLayout(PnloLayout);
        PnloLayout.setHorizontalGroup(
            PnloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnloLayout.createSequentialGroup()
                .addComponent(jLabel22)
                .addGap(0, 534, Short.MAX_VALUE))
        );
        PnloLayout.setVerticalGroup(
            PnloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnloLayout.createSequentialGroup()
                .addComponent(jLabel22)
                .addGap(0, 301, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(Pnlo);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(241, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(488, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phòng Bàn ", jPanel1);

        jLabel2.setText("Thời gian bắt đầu:");

        jLabel3.setText("Thời gian kết thúc:");

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Bắt Đầu");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 0));
        jButton2.setText("Kết Thúc");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Thời gian tạo hóa đơn:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Mã hóa đơn:");

        jLabel7.setText("jLabel6");

        jLabel8.setText("jLabel6");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Bàn:");

        jLabel10.setText("jLabel6");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Menu:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Đồ ăn & thức uống:");

        cbb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("...");

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jSpinner1.setRequestFocusEnabled(false);

        jButton15.setBackground(new java.awt.Color(0, 204, 102));
        jButton15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Xác Nhận");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbb, 0, 167, Short.MAX_VALUE)
                            .addComponent(jSpinner1))
                        .addGap(18, 18, 18)
                        .addComponent(jButton15)))
                .addContainerGap(699, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setText("Tiền giờ:");

        jLabel16.setText("Giảm giá:");

        jLabel17.setText("0.0");

        jLabel18.setText("Ghi chú:");

        jTextField3.setText("0");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Tổng tiền:");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 0, 0));
        jLabel19.setText("0.0");

        jLabel20.setText("Tổng tiền order:\n");

        jLabel21.setText("0.0");

        jButton16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton16.setText("Thanh toán");
        jButton16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton16MouseClicked(evt);
            }
        });

        jLabel24.setText("Tiền bàn:");

        jLabel31.setText("0.0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton16))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel17))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel21)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel31))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(38, 38, 38)))))
                .addContainerGap(670, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel24)
                    .addComponent(jLabel31))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel19)
                    .addComponent(jButton16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Loại Bàn:");

        jLabel28.setText("jLabel6");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Khu vực:");

        jLabel30.setText("jLabel6");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(jTextField1)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(110, 110, 110)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)))
                        .addGap(0, 625, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(jButton2))
                            .addGap(34, 34, 34)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jLabel6))
                            .addGap(12, 12, 12)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel27)
                                .addComponent(jLabel28))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel29)
                                .addComponent(jLabel30))
                            .addGap(11, 11, 11)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Quản lý bàn", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void cbbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbActionPerformed
        // TODO add your handling code here:
        loadDichVuVaoComboBox();
    }//GEN-LAST:event_cbbActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        batDauChoi();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ketThucChoi();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton16MouseClicked
        // TODO add your handling code here:
        thanhToan();
    }//GEN-LAST:event_jButton16MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pn3bang;
    private javax.swing.JPanel Pn3bangVIP;
    private javax.swing.JPanel Pnlo;
    private javax.swing.JPanel PnloVIP;
    private javax.swing.JComboBox<String> cbb;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
