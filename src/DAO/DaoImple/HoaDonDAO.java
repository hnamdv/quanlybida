package DAO.DaoImple;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Date;
import XJDBC.connect;
import MODEl.Hoadon;

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
                        rs.getDouble("TienGio"),
                        rs.getString("TrangThai"),
                        rs.getDate("NgayTao"),
                        rs.getDouble("GiamGia"),
                        rs.getDouble("TienDV"),
                        rs.getDouble("TongTien"),
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

}
