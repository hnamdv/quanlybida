package UI.Panel;

import DAO.DaoImple.BanbidaDAO;
import DAO.DaoImple.BookingDAO;
import DAO.DaoImple.ChitiethoadonDao;
import DAO.DaoImple.DichVuDAO;
import DAO.DaoImple.HoaDonDAO;
import DAO.DaoImple.LoaibanDAO;
import MODEl.Banbida;
import MODEl.Booking;
import MODEl.Chitiethoadon;
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
import java.sql.Timestamp;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.sql.Date;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import javax.swing.JTextArea;

/**
 *
 * @author HP
 */
public class BanBidaPanel extends javax.swing.JPanel {

    private Hoadon hd;
    private Banbida bd;
    private List<Dichvu> danhSachDV;
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
    private String taoMaHoaDon(String maBan) {
        return "HD" + maBan + "_" + (System.currentTimeMillis() % 1000000);
    }

    private String formatVND(double amount) {
        return String.format("%,.0f VND", amount);
    }

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

    private void chonBan(String maBan, String tenBan, String maLoaiBan, String tinhTrang, double giaTheoGio, int tuoiBan, String ghiChu) {
        this.currentMaBan = maBan;
        jTabbedPane1.setSelectedIndex(1); // Chuyển tab sang sử dụng

        lblmaban.setText(tenBan);
        jLabel30.setText(tinhTrang);
        jTextField4.setText(ghiChu != null ? ghiChu : "");

        // Lấy tên loại bàn
        LoaibanDAO loaibanDAO = new LoaibanDAO();
        String tenLoai = loaibanDAO.getTenLoaiByMa(maLoaiBan);
        jLabel28.setText(tenLoai != null ? tenLoai : "Không rõ");

        // Popup thông tin bàn
        JOptionPane.showMessageDialog(this,
                "📌 Tên bàn: " + tenBan
                + "\n🔸 Loại bàn: " + (tenLoai != null ? tenLoai : "Không rõ")
                + "\n📍 Tình trạng: " + tinhTrang
                + "\n📝 Ghi chú: " + (ghiChu != null ? ghiChu : "Không"),
                "Thông tin bàn",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Kiểm tra hóa đơn đang mở
        HoaDonDAO hdDAO = new HoaDonDAO();
        Hoadon hd = hdDAO.getHoaDonDangMoByBan(maBan);

        if (tinhTrang.equalsIgnoreCase("Trong") && hd == null) {
            // ✅ Bàn đang trống và chưa có hóa đơn → tạo mới
            String maHDMoi = taoMaHoaDon(maBan);
            Hoadon hoaDonMoi = new Hoadon();
            hoaDonMoi.setMaHD(maHDMoi);
            hoaDonMoi.setMaBan(maBan);
            hoaDonMoi.setTrangThai("Chờ bắt đầu");
            hoaDonMoi.setNgayTao(new java.sql.Date(System.currentTimeMillis())); // ✅ Quan trọng

            boolean insertResult = new HoaDonDAO().insert(hoaDonMoi); // 👉 chèn vào DB

            if (insertResult) {
                // ✅ Lưu vào biến tạm
                this.hoaDonTamThoi = hoaDonMoi;
                jLabel7.setText(maHDMoi);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Không thể tạo hóa đơn mới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Reset giao diện
            jTextField1.setText("");
            jTextField2.setText("");
            jLabel17.setText("0.0");
            jLabel21.setText("0.0");
            jLabel19.setText("0.0");
            jLabel31.setText(String.valueOf(giaTheoGio));

            jButton1.setEnabled(true);  // Bắt đầu
            jButton2.setEnabled(true); // Kết thúc
        } else if (hd != null) {
            // ✅ Bàn đang sử dụng hoặc có hóa đơn mở
            jLabel7.setText(hd.getMaHD());
            jTextField1.setText(hd.getThoiGianBD() != null ? hd.getThoiGianBD().toString() : "");
            jTextField2.setText(hd.getThoiGianKT() != null ? hd.getThoiGianKT().toString() : "");
            jLabel17.setText(String.valueOf(hd.getTienGio()));
            jLabel21.setText(String.valueOf(hd.getTienDV()));
            jLabel19.setText(String.valueOf(hd.getTongTien()));
            jLabel31.setText(String.valueOf(giaTheoGio));
            jTextField4.setText(hd.getGhiChu());

            this.hoaDonTamThoi = hd; // ✅ Để ketThucChoi() dùng

            jButton1.setEnabled(false);  // Cho phép bắt đầu lại nếu cần (có thể disable nếu muốn)
            jButton2.setEnabled(true); // Mặc định không cho kết thúc nếu chưa xác nhận

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bàn đang sử dụng.\nBạn có muốn kết thúc không?",
                    "Xác nhận kết thúc",
                    JOptionPane.YES_NO_OPTION
            );

            jButton2.setEnabled(confirm == JOptionPane.YES_OPTION);
        } else {
            // ❗ Không thao tác được với trạng thái bất thường
            JOptionPane.showMessageDialog(this,
                    "⚠️ Không thao tác được với trạng thái bàn: " + tinhTrang,
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );
            jButton1.setEnabled(false);
            jButton2.setEnabled(false);
        }
    }

    private void ketThucChoi() {
        String maBan = currentMaBan;

        if (maBan == null || maBan.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Chưa chọn bàn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HoaDonDAO dao = new HoaDonDAO();
        Hoadon hoadon = dao.getHoaDonDangMoByBan(maBan);

        if (hoadon == null) {
            JOptionPane.showMessageDialog(this, "❌ Không tìm thấy hóa đơn đang sử dụng cho bàn này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp thoiGianBD = hoadon.getThoiGianBD();
        if (thoiGianBD == null) {
            JOptionPane.showMessageDialog(this, "❌ Hóa đơn chưa có thời gian bắt đầu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Timestamp thoiGianKT = new Timestamp(System.currentTimeMillis());
        long millis = thoiGianKT.getTime() - thoiGianBD.getTime();
        long soPhut = Math.max(1, millis / (60 * 1000));

        double donGia = 1000;
        double tongTien = soPhut * donGia;
        jLabel17.setText(String.format("%.1f", tongTien));
        hoadon.setThoiGianKT(thoiGianKT);
        hoadon.setTienGio(tongTien); // hoặc tính kỹ lại
        hoadon.setTrangThai("ChuaThanhToan"); // ✅ Không để "Đã thanh toán" tại đây
        dao.capNhatHoaDon(hoadon); // ✅ Cập nhật DB

        this.hd = hoadon;
        this.hoaDonTamThoi = hoadon;

        boolean thanhCong = dao.capNhatHoaDon(hoadon);

        if (thanhCong) {
            // Trả bàn về trạng thái "Trong"
            BanbidaDAO banDao = new BanbidaDAO();
            banDao.capNhatTinhTrang(maBan, "Trong");
            loadDanhSachBan();
            JOptionPane.showMessageDialog(this, "✅ Kết thúc phiên chơi.\nTổng thời gian: " + soPhut + " phút\nTổng tiền: " + tongTien + " VND", "Hoàn tất", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi cập nhật hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        Hoadon hd = hoadon;
        Banbida bd = new BanbidaDAO().getByMaBan(maBan);
        List<Dichvu> dsDV = new DichVuDAO().getDichVuByHoaDon(hd.getMaHD());

    }

    private void batDauChoi() {
        HoaDonDAO hds = new HoaDonDAO();
        String maBan = currentMaBan;

        if (maBan == null || maBan.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Chưa chọn bàn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        BanbidaDAO banDao = new BanbidaDAO();
        Banbida ban = banDao.getByMaBan(maBan);
        String tinhTrang = ban != null ? ban.getTinhTrang() : null;
        if ("DangSuDung".equalsIgnoreCase(tinhTrang)) {
            JOptionPane.showMessageDialog(this, "⚠️ Bàn này đang được sử dụng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maHD = "HD" + maBan + "_" + (System.currentTimeMillis() % 1000000);
        Timestamp gioBD = new Timestamp(System.currentTimeMillis());
        String maNV = phanquyen.user.getMaNV();

        Hoadon hoadonMoi = new Hoadon(
                maHD, maNV, maBan, gioBD, null, 0.0, "Chưa thanh toán",
                new Date(System.currentTimeMillis()), 0.0, 0.0, 0.0, "", null
        );

        // Lưu hóa đơn trước
        if (hds.themHoaDon(hoadonMoi)) {
            banDao.capNhatTinhTrang(maBan, "DangSuDung");
            hoaDonTamThoi = hoadonMoi; // ✅ Gán lại cho toàn cục
            JOptionPane.showMessageDialog(this, "✅ Bắt đầu tính giờ cho bàn " + maBan);
            loadDanhSachBan();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi tạo hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void thanhToan() {
        if (currentMaBan == null || currentMaBan.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Chưa chọn bàn để thanh toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HoaDonDAO hdDAO = new HoaDonDAO();
        BanbidaDAO banDAO = new BanbidaDAO();

        // Lấy hóa đơn đang mở của bàn
        //  Hoadon hd = hdDAO.getHoaDonDangMoByBan(currentMaBan);
        if (hd == null) {
            hd = hoaDonTamThoi;
        }
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "❌ Không có hóa đơn cần thanh toán!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (hd.getThoiGianKT() == null) {
            JOptionPane.showMessageDialog(this, "❌ Chưa kết thúc phiên chơi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tính lại tiền giờ
        Timestamp bd = hd.getThoiGianBD();
        Timestamp kt = hd.getThoiGianKT();
        long millis = kt.getTime() - bd.getTime();
        double gio = millis / (1000.0 * 60 * 60); // giờ

        double giaGio = banDAO.getGiaTheoMaBan(hd.getMaBan());
        double tienGio = gio * giaGio;

        // Lấy từ UI
        double tienDV = parseDoubleSafely(jLabel21.getText());
        double tongTien = tienGio + tienDV;

        int xacNhan = JOptionPane.showConfirmDialog(this,
                "Xác nhận thanh toán bàn " + currentMaBan + "?\nTổng tiền: " + formatVND(tongTien),
                "Xác nhận thanh toán", JOptionPane.YES_NO_OPTION);

        if (xacNhan != JOptionPane.YES_OPTION) {
            return;
        }

        // Cập nhật hóa đơn
        hd.setTienGio(tienGio);
        hd.setTienDV(tienDV);
        hd.setTongTien(tongTien);
        hd.setTrangThai("DaThanhToan");
        hd.setGhiChu(jTextField4.getText());

        try {
            hdDAO.capNhatHoaDon(hd); // cập nhật DB
            banDAO.capNhatTinhTrang(hd.getMaBan(), "Trong"); // cập nhật bàn

            // In hóa đơn
            Banbida ban = banDAO.getByMaBan(currentMaBan);
            List<Dichvu> danhSachDV = new DichVuDAO().getDichVuTheoHoaDon(hd.getMaHD());

            JOptionPane.showMessageDialog(this, "✅ Thanh toán thành công!\nTổng tiền: " + formatVND(tongTien));

            // Reset UI
            currentMaBan = null;
            hoaDonTamThoi = null;
            jLabel7.setText("");
            jTextField1.setText("");
            jTextField2.setText("");
            jLabel17.setText("0.0");
            jLabel21.setText("0.0");
            jLabel19.setText("0.0");
            jLabel31.setText("0.0");
            jTextField4.setText("");

            loadDanhSachBan();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi thanh toán: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    public String taoBillBida(Hoadon hd, Banbida bd, List<Dichvu> danhSachDV, JTextArea txtaBill, String pdfPath) {
        if (hd == null || bd == null) {
            return "❌ Không thể tạo bill, dữ liệu hóa đơn hoặc bàn bị null!";
        }

        long millis = hd.getThoiGianKT().getTime() - hd.getThoiGianBD().getTime();
        double gio = millis / (1000.0 * 60 * 60);
        double tienGio = gio * bd.getGiaTheoGio();
        Chitiethoadon ct = new Chitiethoadon();
        StringBuilder sb = new StringBuilder();
        sb.append("======= HÓA ĐƠN BIDA =======\n");
        sb.append("Mã HĐ: ").append(hd.getMaHD()).append("\n");
        sb.append("Bàn: ").append(bd.getTenBan()).append(" (").append(bd.getMaLoaiBan()).append(")\n");
        sb.append("Thời gian: ").append(hd.getThoiGianBD()).append(" -> ").append(hd.getThoiGianKT()).append("\n");
        sb.append(String.format("Tổng thời gian: %.2f giờ\n", gio));
        sb.append(String.format("Đơn giá: %.0f VND/giờ\n", bd.getGiaTheoGio()));
        sb.append(String.format("Tiền giờ: %.0f VND\n", tienGio));

        double tongDV = 0;
        if (danhSachDV != null && !danhSachDV.isEmpty()) {
            sb.append("\n--- DỊCH VỤ ---\n");
            for (Dichvu dv : danhSachDV) {
                double thanhTien = dv.getDonGia() * ct.getSoLuong();
                tongDV += thanhTien;
                sb.append(dv.getTenDV())
                        .append(" x").append(ct.getSoLuong())
                        .append(" = ").append(String.format("%.0f VND\n", thanhTien));
            }
            sb.append(String.format("Tổng dịch vụ: %.0f VND\n", tongDV));
        }

        sb.append(String.format("Giảm giá: %.0f VND\n", hd.getGiamGia()));
        sb.append("----------------------------\n");
        sb.append(String.format("TỔNG CỘNG: %.0f VND\n", hd.getTongTien()));
        sb.append("============================\n");
        sb.append("Cảm ơn quý khách!\n");

        String billContent = sb.toString();

        // === Ghi ra JTextArea ===
        if (txtaBill != null) {
            txtaBill.setText(billContent);
        }

        // === Ghi ra file PDF ===
        if (pdfPath != null && !pdfPath.trim().isEmpty()) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
                document.open();
                document.add(new Paragraph(billContent));
                document.close();
                System.out.println("✅ PDF đã được tạo: " + pdfPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return billContent;
    }

    private void xuLyThemDichVu() {
        String maBan = currentMaBan;
        if (maBan == null || maBan.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Chưa chọn bàn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy mã hóa đơn đang mở theo bàn
        HoaDonDAO hoaDonDAO = new HoaDonDAO();
        Hoadon hoaDon = hoaDonDAO.getHoaDonDangMoByBan(maBan);
        if (hoaDon == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Không tìm thấy hóa đơn đang mở cho bàn này!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maHD = hoaDon.getMaHD();

        // Lấy thông tin từ combobox và spinner
        String tenDVChon = cbb.getSelectedItem().toString();
        int soLuong = (int) jSpinner1.getValue();

        if (tenDVChon == null || tenDVChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ Chưa chọn dịch vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (soLuong <= 0) {
            JOptionPane.showMessageDialog(this, "❌ Số lượng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tìm dịch vụ theo tên
        DichVuDAO dvDAO = new DichVuDAO();
        Dichvu dvChon = dvDAO.getByTen(tenDVChon); // Bạn cần có hàm này trong DAO

        if (dvChon == null) {
            JOptionPane.showMessageDialog(this, "❌ Không tìm thấy dịch vụ đã chọn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Thêm vào chi tiết hóa đơn
        new ChitiethoadonDao().themDichVuVaoHoaDon(maHD, dvChon.getMaDV(), soLuong, dvChon.getDonGia());

        // Cập nhật vào bảng hoadon
        double tienDV = dvChon.getDonGia() * soLuong;
        hoaDonDAO.capNhatThongTinDichVu(maHD, dvChon.getMaDV(), tienDV);
        jLabel21.setText(String.format("%.1f", tienDV)); // ✅ Hiển thị Tiền DV ra label
        JOptionPane.showMessageDialog(this, "✅ Đã thêm dịch vụ vào hóa đơn!");
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
        lblTgain = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblmaban = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbb = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton15 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        btninHD = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtaBill = new javax.swing.JTextArea();

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
                .addContainerGap(247, Short.MAX_VALUE))
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
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        lblTgain.setText("jLabel6");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Bàn:");

        lblmaban.setText("jLabel6");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel17.setText("0.0");

        jLabel18.setText("Ghi chú:");

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

        btninHD.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btninHD.setText("In hóa đơn");
        btninHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel21))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel17)
                                .addGap(218, 218, 218)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel31)))
                        .addGap(67, 67, 67)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btninHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(495, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(jLabel31))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel17)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btninHD)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel19))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Loại Bàn:");

        jLabel28.setText("jLabel6");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Khu vực:");

        jLabel30.setText("jLabel6");

        txtaBill.setColumns(20);
        txtaBill.setRows(5);
        jScrollPane6.setViewportView(txtaBill);

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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                            .addComponent(lblmaban, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTgain, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane6)
                        .addContainerGap())))
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
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addGap(12, 12, 12)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(lblmaban))
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
                        .addComponent(lblTgain)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6))
                .addContainerGap(219, Short.MAX_VALUE))
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
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 647, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btninHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninHDActionPerformed
        // TODO add your handling code here:

        String maBan = currentMaBan;
        HoaDonDAO dao = new HoaDonDAO();
        Hoadon hoadon = dao.getHoaDonByMaHD(currentMaBan); // hoặc getHoaDonDangMoByBan(maBan)
        if (hoadon == null) {
            JOptionPane.showMessageDialog(this, "❌ Không tìm thấy hóa đơn để in!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BanbidaDAO banDao = new BanbidaDAO();
        Banbida bd = banDao.getByMaBan(maBan);

// Lưu file PDF
        String pdfPath = "D:/hoadon_" + hoadon.getMaHD() + ".pdf";
        String bill = taoBillBida(hoadon, bd, danhSachDV, txtaBill, pdfPath); // ✅ dùng hoadon thay vì hd

        JOptionPane.showMessageDialog(this, "✅ Hóa đơn đã in và lưu: " + pdfPath);

    }//GEN-LAST:event_btninHDActionPerformed

    private void jButton16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton16MouseClicked
        // TODO add your handling code here:
        thanhToan();
    }//GEN-LAST:event_jButton16MouseClicked

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        xuLyThemDichVu();
    }//GEN-LAST:event_jButton15ActionPerformed
    private void cbbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbActionPerformed
        // TODO add your handling code here:
        loadDichVuVaoComboBox();
    }//GEN-LAST:event_cbbActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ketThucChoi();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        batDauChoi();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pn3bang;
    private javax.swing.JPanel Pn3bangVIP;
    private javax.swing.JPanel Pnlo;
    private javax.swing.JPanel PnloVIP;
    private javax.swing.JButton btninHD;
    private javax.swing.JComboBox<String> cbb;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lblTgain;
    private javax.swing.JLabel lblmaban;
    private javax.swing.JTextArea txtaBill;
    // End of variables declaration//GEN-END:variables
}
