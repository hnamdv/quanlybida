/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;

import MODEl.Loaiban;
import java.sql.*;
import java.util.*;
import XJDBC.connect;

/**
 *
 * @author HP
 */

public class LoaibanDAO {

    public static Map<String, Loaiban> getMapLoaiBan() {
        Map<String, Loaiban> map = new HashMap<>();
        String sql = "SELECT * FROM LOAIBAN";

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Loaiban lb = new Loaiban();
                lb.setMaLoaiBan(rs.getString("MaLoaiBan"));
                lb.setTenLoai(rs.getString("TenLoai"));
                lb.setMoTa(rs.getString("MoTa"));
                map.put(lb.getMaLoaiBan(), lb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public String getTenLoaiByMa(String maLoaiBan) {
        String sql = "SELECT TenLoai FROM LOAIBAN WHERE MaLoaiBan = ?";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLoaiBan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("TenLoai");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Không xác định"; // hoặc trả về null nếu bạn muốn kiểm tra null sau này
    }

}
