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
        String sql = "SELECT * FROM hoadon WHERE MaBan = ? AND TrangThai = 'DangMo'";
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
    rs.getDouble("TongTien"),     // đúng vị trí
    rs.getString("TrangThai"),
    rs.getDate("NgayTao"),
    rs.getDouble("TienGio"),
    rs.getDouble("GiamGia"),
    rs.getDouble("TienDV"),
    rs.getString("GhiChu")
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

    public void insert(Hoadon hd) {
 String sql = "INSERT INTO hoadon (MaHD, MaNV, MaBan, ThoiGianBD, TrangThai, NgayTao, TienGio, GiamGia, TienDV, TongTien, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
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
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
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

        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql))  {

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
}

