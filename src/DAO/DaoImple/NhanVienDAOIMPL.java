/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;

import DAO.daointer.NhanVienDAO;
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
public class NhanVienDAOIMPL implements NhanVienDAO {
    
        @Override
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

    @Override
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
}
