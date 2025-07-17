/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;
import MODEl.Calam;
import XJDBC.connect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CaLamDAO {
       public boolean insert(Calam ca) {
        String sql = "INSERT INTO calam (MaCa, TenCa, GioBatDau, GioKetThuc) VALUES (?, ?, ?, ?)";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, ca.getMaCa());
            ps.setString(2, ca.getTenCa());
            ps.setTime(3, ca.getGioBatDau());
            ps.setTime(4, ca.getGioKetThuc());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Calam ca) {
        String sql = "UPDATE calam SET TenCa=?, GioBatDau=?, GioKetThuc=? WHERE MaCa=?";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, ca.getTenCa());
            ps.setTime(2, ca.getGioBatDau());
            ps.setTime(3, ca.getGioKetThuc());
            ps.setString(4, ca.getMaCa());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maCa) {
        String sql = "DELETE FROM calam WHERE MaCa=?";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, maCa);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Calam findById(String maCa) {
        String sql = "SELECT * FROM calam WHERE MaCa=?";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, maCa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Calam(
                    rs.getString("MaCa"),
                    rs.getString("TenCa"),
                    rs.getTime("GioBatDau"),
                    rs.getTime("GioKetThuc")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Calam> getAll() {
        List<Calam> list = new ArrayList<>();
        String sql = "SELECT * FROM calam";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Calam ca = new Calam(
                    rs.getString("MaCa"),
                    rs.getString("TenCa"),
                    rs.getTime("GioBatDau"),
                    rs.getTime("GioKetThuc")
                );
                list.add(ca);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
