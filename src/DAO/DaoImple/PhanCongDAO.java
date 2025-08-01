/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;
import MODEl.PhanCong;
import XJDBC.connect;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class PhanCongDAO {
    
    public boolean insert(PhanCong pc) {
         String sql = "INSERT INTO phancong (MaPC, MaNV, TenCa, GioBatDau, GioKetThuc, NgayLam, HeSoLuong) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, pc.getMaPC());
            ps.setString(2, pc.getMaNV());
            ps.setString(3, pc.getTenCa());
            ps.setTime(4, Time.valueOf(pc.getGioBatDau()));
            ps.setTime(5, Time.valueOf(pc.getGioKetThuc()));
            ps.setDate(6, Date.valueOf(pc.getNgayLam()));
            ps.setDouble(7, pc.getHeSoLuong());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(PhanCong pc) {
    String sql = "UPDATE phancong SET MaNV=?, TenCa=?, GioBatDau=?, GioKetThuc=?, NgayLam=?, HeSoLuong=? WHERE MaPC=?";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, pc.getMaNV());
            ps.setString(2, pc.getTenCa());
            ps.setTime(3, Time.valueOf(pc.getGioBatDau()));
            ps.setTime(4, Time.valueOf(pc.getGioKetThuc()));
            ps.setDate(5, Date.valueOf(pc.getNgayLam()));
            ps.setDouble(6, pc.getHeSoLuong());
            ps.setString(7, pc.getMaPC());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maPC) {
        String sql = "DELETE FROM phancong WHERE MaPC=?";
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

    public PhanCong findById(String maPC) {
        String sql = "SELECT * FROM phancong WHERE MaPC=?";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, maPC);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PhanCong(
                    rs.getString("MaPC"),
                    rs.getString("MaNV"),
                    rs.getString("TenCa"),
                    rs.getTime("GioBatDau").toLocalTime(),
                    rs.getTime("GioKetThuc").toLocalTime(),
                    rs.getDate("NgayLam").toLocalDate(),
                    rs.getDouble("HeSoLuong")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PhanCong> getAll() {
        List<PhanCong> list = new ArrayList<>();
        String sql = "SELECT * FROM phancong";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                PhanCong pc = new PhanCong(
                    rs.getString("MaPC"),
                    rs.getString("MaNV"),
                    rs.getString("TenCa"),
                    rs.getTime("GioBatDau").toLocalTime(),
                    rs.getTime("GioKetThuc").toLocalTime(),
                    rs.getDate("NgayLam").toLocalDate(),
                    rs.getDouble("HeSoLuong")
                );
                list.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public PhanCong findByNgay(String maNV, Date ngay) {
    String sql = "SELECT * FROM PhanCong WHERE MaNV = ? AND NgayLam = ?";
    try (
        Connection conn = connect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setString(1, maNV);
        ps.setDate(2, ngay);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new PhanCong(
                rs.getString("MaPC"),
                rs.getString("MaNV"),
                rs.getString("TenCa"),
                rs.getTime("GioBatDau").toLocalTime(),
                rs.getTime("GioKetThuc").toLocalTime(),
                rs.getDate("NgayLam").toLocalDate(),
                rs.getDouble("HeSoLuong")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    public PhanCong getCaLam(String maNV, LocalDate ngay, LocalTime gioHienTai) {
    PhanCong pc = null;
    String sql = """
        SELECT * FROM PhanCong 
        WHERE MaNV = ? 
          AND NgayLam = ? 
          AND (? BETWEEN DATE_SUB(GioBatDau, INTERVAL 30 MINUTE) 
                    AND DATE_ADD(GioKetThuc, INTERVAL 30 MINUTE))
    """;

    try (
        Connection conn = connect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setString(1, maNV);
        ps.setDate(2, Date.valueOf(ngay));
        ps.setTime(3, Time.valueOf(gioHienTai));

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
       
            LocalTime gioBD = rs.getTime("GioBatDau").toLocalTime();
            LocalTime gioKT = rs.getTime("GioKetThuc").toLocalTime();

            boolean trongThoiGianVao = !gioHienTai.isBefore(gioBD.minusMinutes(30)) &&
                                       !gioHienTai.isAfter(gioBD.plusMinutes(10));

            boolean trongThoiGianRa = !gioHienTai.isBefore(gioKT) &&
                                      !gioHienTai.isAfter(gioKT.plusMinutes(30));

            if (trongThoiGianVao || trongThoiGianRa) {
                pc = new PhanCong();
                pc.setMaPC(rs.getString("MaPC"));
                pc.setMaNV(rs.getString("MaNV"));
                pc.setTenCa(rs.getString("TenCa"));
                pc.setGioBatDau(gioBD);
                pc.setGioKetThuc(gioKT);
                pc.setNgayLam(rs.getDate("NgayLam").toLocalDate());
                pc.setHeSoLuong(rs.getDouble("HeSoLuong"));
                break;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return pc;
}

}
