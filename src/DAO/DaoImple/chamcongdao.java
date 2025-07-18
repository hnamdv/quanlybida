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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */

public class chamcongdao {
    // Trả về tất cả
public List<chamcong> findAll() {
    String sql = "SELECT * FROM ChamCong";
    return selectBySql(sql);
}

// Trả về theo mã nhân viên
public List<chamcong> findByMaNV(String maNV) {
    String sql = "SELECT * FROM ChamCong WHERE MaNV = ?";
    return selectBySql(sql, maNV);
}
private List<chamcong> selectBySql(String sql, Object... args) {
    List<chamcong> list = new ArrayList<>();
    try (
        Connection conn = connect.openConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            chamcong cc = new chamcong();
            cc.setMaCC(rs.getInt("MaCC"));
            cc.setMaNV(rs.getString("MaNV"));
            cc.setGioVao(rs.getTimestamp("GioVao"));
            cc.setGioRa(rs.getTimestamp("GioRa"));
            list.add(cc);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


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
        public boolean delete(int maCC) {
        String sql = "DELETE FROM ChamCong WHERE MaCC = ?";
        try (Connection conn = connect.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, maCC);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
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
public chamcong findByNgay(String maNV, Date ngay) {
    String sql = "SELECT * FROM ChamCong WHERE MaNV = ? AND DATE(GioVao) = ?";
    try (
        Connection conn = connect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setString(1, maNV);
        ps.setDate(2, new java.sql.Date(ngay.getTime())); 
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new chamcong(
                rs.getInt("MaCC"),
                rs.getString("MaNV"),
                rs.getTimestamp("GioVao"),
                rs.getTimestamp("GioRa")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public List<chamcong> findByThangNam(String maNV, int thang, int nam) {
    List<chamcong> list = new ArrayList<>();
    String sql = "SELECT * FROM ChamCong WHERE MaNV = ? AND MONTH(GioVao) = ? AND YEAR(GioVao) = ?";

    try (
        Connection conn = connect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {
        ps.setString(1, maNV);
        ps.setInt(2, thang);
        ps.setInt(3, nam);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            chamcong cc = new chamcong(
                rs.getInt("MaCC"),
                rs.getString("MaNV"),
                rs.getTimestamp("GioVao"),
                rs.getTimestamp("GioRa")
            );
            list.add(cc);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
public int demSoNgayCong(String maNV, int thang, int nam) {
    int soNgayCong = 0;
    String sql = "SELECT COUNT(*) FROM ChamCong WHERE MaNV = ? AND MONTH(NgayLam) = ? AND YEAR(NgayLam) = ? AND TrangThai = 'Có mặt'";
     try (
        Connection conn = connect.openConnection();
        PreparedStatement ps = conn.prepareStatement(sql))
             {

        ps.setString(1, maNV);
        ps.setInt(2, thang);
        ps.setInt(3, nam);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            soNgayCong = rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return soNgayCong;
}



}
