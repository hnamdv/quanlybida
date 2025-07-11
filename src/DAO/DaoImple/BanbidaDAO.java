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
                System.out.println("Đã load: " + ban.getTenBan()); // 🟢 THÊM DÒNG NÀY
                list.add(ban);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
