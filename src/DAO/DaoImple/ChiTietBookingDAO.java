package DAO.DaoImple;


import MODEl.ChiTietBooking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import XJDBC.connect; // class Connect.java của bạn phải có openConnection()

public class ChiTietBookingDAO {

    // Tìm theo mã booking
    public ChiTietBooking findByMaCT(String maCTBooking) {
        Connection conn = connect.openConnection();
        String sql = "SELECT * FROM CT_BOOKING WHERE MaCTBooking = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maCTBooking);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String gioBD = rs.getString("GioBatDau");
                String gioKT = rs.getString("GioKetThuc");
                String ghiChu = rs.getString("GhiChu");

                return new ChiTietBooking(
                    rs.getString("MaCTBooking"),
                    rs.getString("MaBooking"),
                    rs.getString("MaBan"),
                    gioBD != null ? gioBD : "",
                    gioKT != null ? gioKT : "",
                    ghiChu != null ? ghiChu : ""
                );
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm ChiTietBooking: " + e.getMessage());
        }

        return null;
    }

    // Thêm mới
    public boolean insertChiTietBooking(ChiTietBooking ct) {
        Connection conn = connect.openConnection();
        String sql = "INSERT INTO CT_BOOKING (MaCTBooking, MaBooking, MaBan, GioBatDau, GioKetThuc, GhiChu) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ct.getMaCTBooking());
            ps.setString(2, ct.getMaBooking());
            ps.setString(3, ct.getMaBan());
            ps.setString(4, ct.getGioBatDau());
            ps.setString(5, ct.getGioKetThuc());
            ps.setString(6, ct.getGhiChu());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi insert ChiTietBooking: " + e.getMessage());
            return false;
        }
    }

    // Lấy toàn bộ
    public List<ChiTietBooking> getAllChiTietBooking() {
        List<ChiTietBooking> list = new ArrayList<>();
        Connection conn = connect.openConnection();
        String sql = "SELECT * FROM CT_BOOKING";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String gioBD = rs.getString("GioBatDau");
                String gioKT = rs.getString("GioKetThuc");
                String ghiChu = rs.getString("GhiChu");

                ChiTietBooking ct = new ChiTietBooking(
                    rs.getString("MaCTBooking"),
                    rs.getString("MaBooking"),
                    rs.getString("MaBan"),
                    gioBD != null ? gioBD : "",
                    gioKT != null ? gioKT : "",
                    ghiChu != null ? ghiChu : ""
                );
                list.add(ct);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAll CT_BOOKING: " + e.getMessage());
        }

        return list;
    }
    public List<ChiTietBooking> findAll() {
        List<ChiTietBooking> list = new ArrayList<>();
        Connection conn = connect.openConnection();
        String sql = "SELECT * FROM CT_BOOKING";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietBooking ct = new ChiTietBooking(
                    rs.getString("MaCTBooking"),
                    rs.getString("MaBooking"),
                    rs.getString("MaBan"),
                    rs.getString("GioBatDau"),
                    rs.getString("GioKetThuc"),
                    rs.getString("GhiChu")
                );
                list.add(ct);
            }
        } catch (Exception e) {
            System.err.println("Lỗi findAll: " + e.getMessage());
        }
        return list;
    }
}
