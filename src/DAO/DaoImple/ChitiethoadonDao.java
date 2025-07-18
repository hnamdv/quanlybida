/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;

import MODEl.Chitiethoadon;
import XJDBC.connect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChitiethoadonDao {
public List<Chitiethoadon> getAll() {
        List<Chitiethoadon> list = new ArrayList<>();
        String sql = "SELECT MaCT, MaHD, MaDV, SoLuong, DonGia FROM CT_HOADON_DICHVU";

        try (Connection con = connect.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Chitiethoadon ct = new Chitiethoadon();
                ct.setMaCT(rs.getString("MaCT"));
                ct.setMaHD(rs.getString("MaHD"));
                ct.setMaDV(rs.getString("MaDV"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGia(rs.getDouble("DonGia")); // dùng double như yêu cầu
                list.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
   public boolean insert(Chitiethoadon ct) {
        String sql = "INSERT INTO CT_HOADON_DICHVU (MaCT, MaHD, MaDV, SoLuong, DonGia) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = connect.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ct.getMaCT());
            ps.setString(2, ct.getMaHD());
            ps.setString(3, ct.getMaDV());
            ps.setInt(4, ct.getSoLuong());
            ps.setDouble(5, ct.getDonGia());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
   public boolean update(Chitiethoadon ct) {
    String sql = "UPDATE CT_HOADON_DICHVU SET MaHD = ?, MaDV = ?, SoLuong = ?, DonGia = ? WHERE MaCT = ?";
    try (Connection con = connect.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
         
        ps.setString(1, ct.getMaHD());
        ps.setString(2, ct.getMaDV());
        ps.setInt(3, ct.getSoLuong());
        ps.setDouble(4, ct.getDonGia());
        ps.setString(5, ct.getMaCT());

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
public boolean delete(String maCT) {
    String sql = "DELETE FROM CT_HOADON_DICHVU WHERE MaCT = ?";
    try (Connection con = connect.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, maCT);
        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

}


