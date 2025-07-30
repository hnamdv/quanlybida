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
        try (
                Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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

}
