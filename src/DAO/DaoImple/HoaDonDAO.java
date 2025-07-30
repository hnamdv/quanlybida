package DAO.DaoImple;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Date;
import XJDBC.connect;
import MODEl.Hoadon;
import java.util.HashMap;
import java.util.Map;

public class HoaDonDAO {

    public Hoadon getHoaDonDangMoByBan(String maBan) {
        String sql = "SELECT * FROM hoadon WHERE MaBan = ? AND TrangThai = 'Chưa thanh toán'";
        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Hoadon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaBan"),
                        rs.getTimestamp("ThoiGianBD"),
                        rs.getTimestamp("ThoiGianKT"),
                        rs.getDouble("TongTien"),
                        rs.getString("TrangThai"),
                        rs.getDate("NgayTao"),
                        rs.getDouble("TienGio"),
                        rs.getDouble("GiamGia"),
                        rs.getDouble("TienDV"),
                        rs.getString("GhiChu"),
                        rs.getString("MaDV")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTenLoaiByMa(String maLoai) {
        String sql = "SELECT TenLoai FROM LoaiBan WHERE MaLoai = ?";
        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maLoai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("TenLoai");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Hoadon hd) {
        String sql = """
        INSERT INTO hoadon (
            MaHD, MaNV, MaBan, ThoiGianBD, TrangThai,
            NgayTao, TienGio, GiamGia, TienDV, TongTien, GhiChu
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (
                Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hd.getMaHD());
            ps.setString(2, hd.getMaNV());
            ps.setString(3, hd.getMaBan());
            ps.setTimestamp(4, hd.getThoiGianBD());
            ps.setString(5, hd.getTrangThai());
            ps.setDate(6, new java.sql.Date(hd.getNgayTao().getTime()));
            ps.setDouble(7, hd.getTienGio());
            ps.setDouble(8, hd.getGiamGia());
            ps.setDouble(9, hd.getTienDV());
            ps.setDouble(10, hd.getTongTien());
            ps.setString(11, hd.getGhiChu());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Thêm hoá đơn thành công: " + hd.getMaHD());
                return true;
            } else {
                System.err.println("⚠️ Không thêm được hoá đơn: " + hd.getMaHD());
                return false;
            }

        } catch (Exception ex) {
            System.err.println("❌ Lỗi khi thêm hoá đơn vào database: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public void update(Hoadon hd) {
        String sql = "UPDATE hoadon SET ThoiGianKT = ?, TienGio = ?, TongTien = ?, TrangThai = ? WHERE MaHD = ?";
        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, hd.getThoiGianKT());
            ps.setDouble(2, hd.getTienGio());
            ps.setDouble(3, hd.getTongTien());
            ps.setString(4, hd.getTrangThai());
            ps.setString(5, hd.getMaHD());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Double> getDoanhThuTheoThang(int nam) {
        Map<Integer, Double> map = new HashMap<>();
        String sql = "SELECT MONTH(NgayTao) AS Thang, SUM(TongTien) AS DoanhThu "
                + "FROM hoadon WHERE YEAR(NgayTao) = ? GROUP BY MONTH(NgayTao) ORDER BY Thang";

        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nam);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int thang = rs.getInt("Thang");
                double tien = rs.getDouble("DoanhThu");
                map.put(thang, tien);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public boolean themHoaDon(Hoadon hd) {
        String sql = "INSERT INTO hoadon (MaHD, MaNV, MaBan, ThoiGianBD, ThoiGianKT, TongTien, TrangThai, NgayTao, TienGio, GiamGia, TienDV, GhiChu, MaDV) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hd.getMaHD());
            ps.setString(2, hd.getMaNV());
            ps.setString(3, hd.getMaBan());
            ps.setTimestamp(4, hd.getThoiGianBD());
            ps.setTimestamp(5, hd.getThoiGianKT());
            ps.setDouble(6, hd.getTongTien());
            ps.setString(7, hd.getTrangThai());
            ps.setDate(8, new java.sql.Date(hd.getNgayTao().getTime()));
            ps.setDouble(9, hd.getTienGio());
            ps.setDouble(10, hd.getGiamGia());
            ps.setDouble(11, hd.getTienDV());
            ps.setString(12, hd.getGhiChu());
            ps.setString(13, hd.getMaDV());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean capNhatHoaDon(Hoadon hd) {
        String sql = "UPDATE hoadon SET MaNV = ?, MaBan = ?, ThoiGianBD = ?, ThoiGianKT = ?, TongTien = ?, "
                + "TrangThai = ?, NgayTao = ?, TienGio = ?, GiamGia = ?, TienDV = ?, GhiChu = ?, MaDV = ? "
                + "WHERE MaHD = ?";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hd.getMaNV());
            ps.setString(2, hd.getMaBan());
            ps.setTimestamp(3, hd.getThoiGianBD());
            ps.setTimestamp(4, hd.getThoiGianKT());
            ps.setDouble(5, hd.getTongTien());
            ps.setString(6, hd.getTrangThai());
            ps.setDate(7, new java.sql.Date(hd.getNgayTao().getTime()));
            ps.setDouble(8, hd.getTienGio());
            ps.setDouble(9, hd.getGiamGia());
            ps.setDouble(10, hd.getTienDV());
            ps.setString(11, hd.getGhiChu());
            ps.setString(12, hd.getMaDV());
            ps.setString(13, hd.getMaHD());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Hoadon getHoaDonByMaHD(String maHD) {
        String sql = "SELECT * FROM hoadon WHERE MaHD = ?";
        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Hoadon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaBan"),
                        rs.getTimestamp("ThoiGianBD"),
                        rs.getTimestamp("ThoiGianKT"),
                        rs.getDouble("TongTien"),
                        rs.getString("TrangThai"),
                        rs.getDate("NgayTao"),
                        rs.getDouble("TienGio"),
                        rs.getDouble("GiamGia"),
                        rs.getDouble("TienDV"),
                        rs.getString("GhiChu"),
                        rs.getString("MaDV")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void capNhatThongTinDichVu(String maHD, String maDV, double tienDV) {
        String sql = "UPDATE hoadon SET MaDV = ?, TienDV = ? WHERE MaHD = ?";

        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maDV);
            ps.setDouble(2, tienDV);
            ps.setString(3, maHD);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Đã cập nhật dịch vụ cho hóa đơn: " + maHD);
            } else {
                System.out.println("⚠️ Không tìm thấy hóa đơn: " + maHD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Hoadon getHoaDonGanNhatByMaBan(String maBan) {
    String sql = "SELECT * FROM hoadon WHERE MaBan = ? ORDER BY NgayTao DESC LIMIT 1";
    try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, maBan);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Hoadon(
                    rs.getString("MaHD"),
                    rs.getString("MaNV"),
                    rs.getString("MaBan"),
                    rs.getTimestamp("ThoiGianBD"),
                    rs.getTimestamp("ThoiGianKT"),
                    rs.getDouble("TongTien"),
                    rs.getString("TrangThai"),
                    rs.getDate("NgayTao"),
                    rs.getDouble("TienGio"),
                    rs.getDouble("GiamGia"),
                    rs.getDouble("TienDV"),
                    rs.getString("GhiChu"),
                    rs.getString("MaDV")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

}
