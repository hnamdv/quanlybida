/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;

import MODEl.Banbida;
import java.sql.*;
import java.util.*;
import XJDBC.connect;

/**
 *
 * @author HP
 */
public class BanbidaDAO {

    public List<Banbida> getAll() {
        List<Banbida> list = new ArrayList<>();
        String sql = "SELECT * FROM BANBIDA";
        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Banbida ban = new Banbida();
                ban.setMaBan(rs.getString("MaBan"));
                ban.setTenBan(rs.getString("TenBan"));
                ban.setMaLoaiBan(rs.getString("MaLoaiBan"));
                ban.setTinhTrang(rs.getString("TinhTrang"));
                ban.setGiaTheoGio(rs.getDouble("GiaTheoGio"));
                ban.setTuoiBan(rs.getInt("TuoiBan"));
                ban.setGhiChu(rs.getString("GhiChu"));
                list.add(ban);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean capNhatTinhTrang(String maBan, String tinhTrang) {
        String sql = "UPDATE BANBIDA SET TinhTrang = ? WHERE MaBan = ?";
        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tinhTrang);
            ps.setString(2, maBan);
            System.out.println("SQL: " + ps.toString());
            int rows = ps.executeUpdate();
            System.out.println("Số dòng bị ảnh hưởng: " + rows);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getGiaTheoMaBan(String maBan) {
        String sql = "SELECT GiaTheoGio FROM Banbida WHERE MaBan = ?";
        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maBan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("GiaTheoGio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public List<Banbida> getByTinhTrang(String tinhTrang) {
        List<Banbida> list = new ArrayList<>();
        String sql = "SELECT * FROM BANBIDA WHERE TinhTrang = ?";

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tinhTrang);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Banbida b = new Banbida(
                        rs.getString("MaBan"),
                        rs.getString("TenBan"),
                        rs.getString("MaLoaiBan"),
                        rs.getString("TinhTrang"),
                        rs.getDouble("GiaTheoGio"),
                        rs.getInt("TuoiBan"),
                        rs.getString("GhiChu")
                );
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Banbida getByMaBan(String maBan) {
        Banbida ban = null;
        String sql = "SELECT * FROM banbida WHERE MaBan = ?";

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maBan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ban = new Banbida();
                    ban.setMaBan(rs.getString("MaBan"));
                    ban.setTenBan(rs.getString("TenBan"));
                    ban.setMaLoaiBan(rs.getString("MaLoaiBan"));
                    ban.setTinhTrang(rs.getString("TinhTrang"));
                    ban.setGiaTheoGio(rs.getDouble("GiaTheoGio"));
                    ban.setTuoiBan(rs.getInt("TuoiBan"));
                    ban.setGhiChu(rs.getString("GhiChu"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ban;
    }

    public List<String> getAllMaBanDangTrong() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT MaBan FROM BANBIDA WHERE TinhTrang = 'Trong' OR TinhTrang = 'DangSua' OR TinhTrang = 'BaoTri'";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("MaBan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Banbida ban) {
        String sql = "INSERT INTO BANBIDA (MaBan, TenBan, MaLoaiBan, TinhTrang, GiaTheoGio, TuoiBan, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ban.getMaBan());
            ps.setString(2, ban.getTenBan());
            ps.setString(3, ban.getMaLoaiBan());
            ps.setString(4, ban.getTinhTrang());
            ps.setDouble(5, ban.getGiaTheoGio());
            ps.setInt(6, ban.getTuoiBan());
            ps.setString(7, ban.getGhiChu());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Banbida ban) {
        String sql = "UPDATE BANBIDA SET TenBan = ?, MaLoaiBan = ?, TinhTrang = ?, GiaTheoGio = ?, TuoiBan = ?, GhiChu = ? WHERE MaBan = ?";
        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ban.getTenBan());
            ps.setString(2, ban.getMaLoaiBan());
            ps.setString(3, ban.getTinhTrang());
            ps.setDouble(4, ban.getGiaTheoGio());
            ps.setInt(5, ban.getTuoiBan());
            ps.setString(6, ban.getGhiChu());
            ps.setString(7, ban.getMaBan());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maBan) {
        String updateHoaDonSQL = "UPDATE HOADON SET MaBan = 'B000' WHERE MaBan = ?";
        String insertBanAoSQL = "INSERT IGNORE INTO BANBIDA (MaBan, TenBan, TinhTrang) VALUES ('B000', 'Bàn đã xóa', 'Ngưng hoạt động')";
        String deleteBanSQL = "DELETE FROM BANBIDA WHERE MaBan = ?";

        try (Connection conn = connect.openConnection()) {
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1. Tạo bàn ảo nếu chưa có
            try (PreparedStatement ps = conn.prepareStatement(insertBanAoSQL)) {
                ps.executeUpdate();
            }

            // 2. Chuyển hóa đơn của bàn cần xóa sang bàn ảo
            try (PreparedStatement ps = conn.prepareStatement(updateHoaDonSQL)) {
                ps.setString(1, maBan);
                ps.executeUpdate();
            }

            // 3. Xóa bàn
            try (PreparedStatement ps = conn.prepareStatement(deleteBanSQL)) {
                ps.setString(1, maBan);
                int rows = ps.executeUpdate();
                conn.commit();
                return rows > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
