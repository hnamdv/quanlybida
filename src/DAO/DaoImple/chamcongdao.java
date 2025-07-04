/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;

import MODEl.chamcong;
import XJDBC.connect;
import java.sql.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */

public class chamcongdao {
    
    // Chấm công vào
    public boolean insertChamCong(String maNV, Timestamp gioVao) {
        String sql = "INSERT INTO ChamCong (MaNV, GioVao, Ngay) VALUES (?, ?, CURDATE())";
        try (Connection conn = connect.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            stmt.setTimestamp(2, gioVao);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Lỗi insert chấm công: " + e.getMessage());
            return false;
        }
    }

    // Kết ca (cập nhật giờ ra)
    public boolean updateGioRa(String maNV, Timestamp gioRa) {
        String sql = "UPDATE ChamCong SET GioRa = ? WHERE MaNV = ? AND Ngay = CURDATE() AND GioRa IS NULL";
        try (Connection conn = connect.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, gioRa);
            stmt.setString(2, maNV);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Lỗi update giờ ra: " + e.getMessage());
            return false;
        }
    }

    public chamcong findTodayRecord(String maNV) {
        String sql = "SELECT * FROM ChamCong WHERE MaNV = ? AND Ngay = CURDATE()";
        try (Connection conn = connect.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                chamcong cc = new chamcong();
                cc.setMaCC(rs.getInt("MaCC"));
                cc.setMaNV(rs.getString("MaNV"));
                cc.setGioVao(rs.getTimestamp("GioVao"));
                cc.setGioRa(rs.getTimestamp("GioRa"));
                
                return cc;
            }
        } catch (Exception e) {
            System.err.println("Lỗi tìm chấm công: " + e.getMessage());
        }
        return null;
    }
    public boolean daMoCa(String maNV) {
    String sql = "SELECT * FROM ChamCong WHERE MaNV = ? AND Ngay = CURDATE() AND GioRa IS NULL";
    try (Connection conn = connect.openConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, maNV);
        ResultSet rs = stmt.executeQuery();
        return rs.next(); // Nếu có bản ghi => đã mở ca
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
public boolean daChamCongHomNay(String maNV) {
    String sql = "SELECT * FROM ChamCong WHERE MaNV = ? AND Ngay = CURDATE()";
    try (Connection conn = connect.openConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, maNV);
        ResultSet rs = stmt.executeQuery();
        return rs.next(); // nếu có bản ghi rồi => đã chấm công
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}
