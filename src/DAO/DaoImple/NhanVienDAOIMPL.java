/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;

import MODEl.Nhanvien;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import XJDBC.connect;
import static java.util.Collections.list;

/**
 *
 * @author Admin
 */
public class NhanVienDAOIMPL {
    
    
    public List<Nhanvien> getAll() {
        List<Nhanvien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";

        try (
            Connection conn = connect.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Nhanvien nv = new Nhanvien(
        rs.getString("MaNV"),
        rs.getString("HoTen"),
        rs.getString("GioiTinh"),
        rs.getDate("NgaySinh"),
        rs.getString("SDT"),
        rs.getString("Email"),
        rs.getString("ChucVu"),
        rs.getString("MatKhau"),
        rs.getBoolean("TrangThai")
    );
    list.add(nv);
                list.add(nv);
            }
        } catch (Exception e) {
            System.err.println("Lỗi lấy dữ liệu nhân viên: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }


    public Nhanvien findById(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ?";
        try (
            Connection conn = connect.openConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, maNV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new   Nhanvien(
        rs.getString("MaNV"),
        rs.getString("HoTen"),
        rs.getString("GioiTinh"),
        rs.getDate("NgaySinh"),
        rs.getString("SDT"),
        rs.getString("Email"),
        rs.getString("ChucVu"),
        rs.getString("MatKhau"),
        rs.getBoolean("TrangThai")
    );
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi tìm nhân viên: " + e.getMessage());
        }
        return null;
    }


public boolean update(Nhanvien nv) {
    String sql = "UPDATE NhanVien SET HoTen=?, GioiTinh=?, NgaySinh=?, SDT=?, Email=?, ChucVu=?, MatKhau=?, TrangThai=? WHERE MaNV=?";
    try (
        Connection conn = connect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setString(1, nv.getHoTen());
        ps.setString(2, nv.getGioiTinh());
        ps.setDate(3, nv.getNgaySinh());
        ps.setString(4, nv.getSdt());
        ps.setString(5, nv.getEmail());
        ps.setString(6, nv.getChucVu());
        ps.setString(7, nv.getMatKhau());
        ps.setBoolean(8, nv.isTrangThai());
        ps.setString(9, nv.getMaNV());

        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


public Nhanvien findByTenDangNhap(String tenDangNhap) {
    String sql = "SELECT * FROM NhanVien WHERE MaNV = ?";
    try (
        Connection conn = connect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setString(1, tenDangNhap);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Nhanvien(
                rs.getString("MaNV"),
                rs.getString("HoTen"),
                rs.getString("GioiTinh"),
                rs.getDate("NgaySinh"),
                rs.getString("SDT"),
                rs.getString("Email"),
                rs.getString("ChucVu"),
                rs.getString("MatKhau"),
                rs.getBoolean("TrangThai")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


public Nhanvien checkLogin(String maNV, String matKhau) {
    String sql = "SELECT * FROM NHANVIEN WHERE MaNV = ? AND MatKhau = ?";
    try (
        Connection conn = connect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setString(1, maNV);
        ps.setString(2, matKhau);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Nhanvien(
                rs.getString("MaNV"),
                rs.getString("HoTen"),
                rs.getString("GioiTinh"),
                rs.getDate("NgaySinh"),
                rs.getString("SDT"),
                rs.getString("Email"),
                rs.getString("ChucVu"),
                rs.getString("MatKhau"),
                rs.getBoolean("TrangThai")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


public boolean doiMatKhau(String maNV, String matKhauMoi) {
    String sql = "UPDATE NhanVien SET MatKhau = ? WHERE MaNV = ?";
    try (
        Connection conn = connect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setString(1, matKhauMoi);
        ps.setString(2, maNV);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

  

          
}