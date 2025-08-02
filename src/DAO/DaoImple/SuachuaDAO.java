/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;

import MODEl.Banbida;
import MODEl.Suachua;
import XJDBC.connect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

/**
 *
 * @author HP
 */
public class SuachuaDAO {

    public List<Suachua> getAllBanDangSua() {
        List<Suachua> list = new ArrayList<>();
        String sql = """
        SELECT s.MaSC, s.MaBan, s.MoTaLoi, s.ChiPhi, s.NgaySua
        FROM SUACHUA s
        JOIN BANBIDA b ON s.MaBan = b.MaBan
        WHERE b.TinhTrang IN ('DangSua', 'BaoTri')
    """;

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Suachua sc = new Suachua(
                        rs.getString("MaSC"),
                        rs.getString("MaBan"),
                        rs.getString("MoTaLoi"),
                        rs.getDouble("ChiPhi"),
                        rs.getDate("NgaySua")
                );
                list.add(sc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Suachua sc) {
        String sql = "INSERT INTO SUACHUA (MaSC, MaBan, MoTaLoi, ChiPhi, NgaySua) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sc.getMaSC());
            ps.setString(2, sc.getMaBan());
            ps.setString(3, sc.getMoTaLoi());
            ps.setDouble(4, sc.getChiPhi());
            ps.setDate(5, sc.getNgaySua());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Suachua> getByNgaySua(Date ngay) {
        List<Suachua> list = new ArrayList<>();
        String sql = "SELECT * FROM SUACHUA WHERE DATE(NgaySua) = ?";

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, ngay);  // vẫn giữ nguyên
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Suachua sc = new Suachua(
                        rs.getString("MaSC"),
                        rs.getString("MaBan"),
                        rs.getString("MoTaLoi"),
                        rs.getDouble("ChiPhi"),
                        rs.getDate("NgaySua")
                );
                list.add(sc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Suachua getThongTinSuaGanNhat(String maBan) {
        String sql = "SELECT * FROM SUACHUA WHERE MaBan = ? ORDER BY NgaySua DESC LIMIT 1";

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maBan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Suachua(
                        rs.getString("MaSC"),
                        rs.getString("MaBan"),
                        rs.getString("MoTaLoi"),
                        rs.getDouble("ChiPhi"),
                        rs.getDate("NgaySua")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void capNhatTinhTrang(String maBan, String tinhTrangMoi) {
        String sql = "UPDATE BANBIDA SET TinhTrang = ? WHERE MaBan = ?";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tinhTrangMoi);
            ps.setString(2, maBan);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getDanhSachMaBanDangSua() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT MaBan FROM BANBIDA WHERE TinhTrang = ('DangSua', 'BaoTri')";

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("MaBan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertSuachuaVaBan(Suachua sc, List<String> danhSachMaBan) {
        String sqlSuachua = "INSERT INTO SUACHUA (MaSC, MoTaLoi, ChiPhi, NgaySua) VALUES (?, ?, ?, ?)";
        String sqlCT = "INSERT INTO CT_SUACHUA (MaSC, MaBan) VALUES (?, ?)";

        try (Connection con = connect.openConnection()) {
            con.setAutoCommit(false); // ✅ Dùng transaction

            try (PreparedStatement ps1 = con.prepareStatement(sqlSuachua)) {
                ps1.setString(1, sc.getMaSC());
                ps1.setString(2, sc.getMoTaLoi());
                ps1.setDouble(3, sc.getChiPhi());
                ps1.setDate(4, sc.getNgaySua());
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = con.prepareStatement(sqlCT)) {
                for (String maBan : danhSachMaBan) {
                    ps2.setString(1, sc.getMaSC());
                    ps2.setString(2, maBan);
                    ps2.addBatch();
                }
                ps2.executeBatch();
            }

            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertSuaChuaNhieuBan(Suachua sc, List<String> maBanList) {
        String sql = "INSERT INTO SUACHUA (MaSC, MaBan, MoTaLoi, ChiPhi, NgaySua) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (String maBan : maBanList) {
                ps.setString(1, sc.getMaSC());
                ps.setString(2, maBan);
                ps.setString(3, sc.getMoTaLoi());
                ps.setDouble(4, sc.getChiPhi());
                ps.setDate(5, sc.getNgaySua());
                ps.addBatch();

                // ✅ Cập nhật tình trạng bàn về "DangSua" hoặc "BaoTri"
                capNhatTinhTrang(maBan, "BaoTri");

            }
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
