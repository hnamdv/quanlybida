/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;

import MODEl.Phancongca;
import XJDBC.connect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Admin
 */
public class PhanCongCaDAO {
    
public boolean insert(Phancongca pcc) {
        String sql = "INSERT INTO phancong_ca (MaPC, MaNV, MaCa, NgayLam, HeSoLuong, LuongThuong) VALUES (?, ?, ?, ?, ?, ?)";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, pcc.getMaPC());
            ps.setString(2, pcc.getMaNV());
            ps.setString(3, pcc.getMaCa());
            ps.setDate(4, Date.valueOf(pcc.getNgayLam()));
            ps.setDouble(5, pcc.getHeSoLuong());
            ps.setDouble(6, pcc.getLuongThuong());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Phancongca pcc) {
        String sql = "UPDATE phancong_ca SET MaNV=?, MaCa=?, NgayLam=?, HeSoLuong=?, LuongThuong=? WHERE MaPC=?";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, pcc.getMaNV());
            ps.setString(2, pcc.getMaCa());
            ps.setDate(3, Date.valueOf(pcc.getNgayLam()));
            ps.setDouble(4, pcc.getHeSoLuong());
            ps.setDouble(5, pcc.getLuongThuong());
            ps.setString(6, pcc.getMaPC());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maPC) {
        String sql = "DELETE FROM phancong_ca WHERE MaPC=?";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, maPC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Phancongca findById(String maPC) {
        String sql = "SELECT * FROM phancong_ca WHERE MaPC=?";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, maPC);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Phancongca(
                    rs.getString("MaPC"),
                    rs.getString("MaNV"),
                    rs.getString("MaCa"),
                    rs.getDate("NgayLam").toLocalDate(),
                    rs.getDouble("HeSoLuong"),
                    rs.getDouble("LuongThuong")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Phancongca> getAll() {
        List<Phancongca> list = new ArrayList<>();
        String sql = "SELECT * FROM phancong_ca";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Phancongca pcc = new Phancongca(
                    rs.getString("MaPC"),
                    rs.getString("MaNV"),
                    rs.getString("MaCa"),
                    rs.getDate("NgayLam").toLocalDate(),
                    rs.getDouble("HeSoLuong"),
                    rs.getDouble("LuongThuong")
                );
                list.add(pcc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

