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

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

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
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

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
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maCT);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean themDichVuVaoHoaDon(String maHD, String maDV, int soLuong, double donGia) {
        String sql = "INSERT INTO ct_hoadon_dichvu (MaCT, MaHD, MaDV, SoLuong, DonGia) VALUES (?, ?, ?, ?, ?)";
        String maCT = "CT" + String.format("%06d", (int) (Math.random() * 1000000));

        try (Connection conn = connect.openConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maCT);
            stmt.setString(2, maHD);
            stmt.setString(3, maDV);
            stmt.setInt(4, soLuong);
            stmt.setDouble(5, donGia);

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Đừng nuốt lỗi
            return false;
        }
    }

    public List<Chitiethoadon> getChiTietByMaHD(String maHD) {
        List<Chitiethoadon> list = new ArrayList<>();
        String sql = "SELECT * FROM CT_HOADON_DICHVU WHERE MaHD = ?";

        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chitiethoadon ct = new Chitiethoadon();
                ct.setMaHD(rs.getString("MaHD"));
                ct.setMaDV(rs.getString("MaDV"));
                ct.setSoLuong(rs.getInt("SoLuong"));
                ct.setDonGia(rs.getDouble("DonGia")); // nếu cần
                list.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public double tinhTongTienDV(String maHD) {
        double tongTien = 0;
        String sql = "SELECT SUM(SoLuong * DonGia) AS TongTien FROM CT_HOADON_DICHVU WHERE MaHD = ?";

        try (Connection conn = connect.openConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tongTien = rs.getDouble("TongTien");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tongTien;
    }

}
