/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;

import XJDBC.connect;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
/**
 *
 * @author HP
 */
public class DichVuDAO {
        public List<String> getAllTenDichVu() {
    List<String> list = new ArrayList<>();
    String sql = "SELECT TenDV FROM DICHVU";

    try (Connection con = connect.openConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            list.add(rs.getString("TenDV")); 
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
