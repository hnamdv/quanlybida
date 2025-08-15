// Updated chamcongdao.java to reflect new chamcong model with MaPC
package DAO.DaoImple;

import MODEl.chamcong;
import XJDBC.connect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class chamcongdao {

    private List<chamcong> selectBySql(String sql, Object... args) {
        List<chamcong> list = new ArrayList<>();
        try (Connection conn = connect.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                chamcong cc = new chamcong(
                    rs.getInt("MaCC"),
                    rs.getString("MaNV"),
                    rs.getTimestamp("GioVao"),
                    rs.getTimestamp("GioRa"),
                    rs.getString("MaPC")
                );
                list.add(cc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<chamcong> findAll() {
        return selectBySql("SELECT * FROM ChamCong");
    }

    public List<chamcong> findByMaNV_ThangNam(String maNV, int thang, int nam) {
        return selectBySql("SELECT * FROM ChamCong WHERE MaNV = ? AND MONTH(Ngay) = ? AND YEAR(Ngay) = ?", maNV, thang, nam);
    }

    public List<chamcong> findAllByThangNam(int thang, int nam) {
        return selectBySql("SELECT * FROM ChamCong WHERE MONTH(Ngay) = ? AND YEAR(Ngay) = ?", thang, nam);
    }

    public List<chamcong> findByMaNV(String maNV) {
        return selectBySql("SELECT * FROM ChamCong WHERE MaNV = ?", maNV);
    }

public boolean insertChamCong(String maNV, String maPC, Timestamp gioVao) {
    String sqlCheck = "SELECT COUNT(*) FROM chamcong WHERE MaPC = ?";
    String sqlInsert = "INSERT INTO chamcong (MaNV, MaPC, GioVao) VALUES (?, ?, ?)";

    try (Connection con = connect.openConnection();
         PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {

        psCheck.setString(1, maPC);
        ResultSet rs = psCheck.executeQuery();

        if (rs.next() && rs.getInt(1) > 0) {
            System.out.println("⚠️ Đã chấm công vào ca này rồi.");
            return false; 
        }

        try (PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {
            psInsert.setString(1, maNV);
            psInsert.setString(2, maPC);
            psInsert.setTimestamp(3, gioVao);
            return psInsert.executeUpdate() > 0;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

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
        try (Connection conn = connect.openConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM ChamCong WHERE MaCC = ?")) {
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
                return new chamcong(
                    rs.getInt("MaCC"),
                    rs.getString("MaNV"),
                    rs.getTimestamp("GioVao"),
                    rs.getTimestamp("GioRa"),
                    rs.getString("MaPC")
                );
            }
        } catch (Exception e) {
            System.err.println("Lỗi tìm chấm công: " + e.getMessage());
        }
        return null;
    }

   public boolean daMoCa(String maNV, String maPC) {
    String sql = "SELECT COUNT(*) FROM ChamCong WHERE MaNV = ? AND MaPC = ? AND GioRa IS NULL";
    try (Connection conn = connect.openConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, maNV);
        ps.setString(2, maPC);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;  
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


    public boolean daChamCongHomNay(String maNV) {
        String sql = "SELECT * FROM ChamCong WHERE MaNV = ? AND Ngay = CURDATE()";
        try (Connection conn = connect.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            return stmt.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public chamcong findByNgay(String maNV, Date ngay) {
        String sql = "SELECT * FROM ChamCong WHERE MaNV = ? AND DATE(GioVao) = ?";
        try (Connection conn = connect.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNV);
            ps.setDate(2, new java.sql.Date(ngay.getTime()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new chamcong(
                    rs.getInt("MaCC"),
                    rs.getString("MaNV"),
                    rs.getTimestamp("GioVao"),
                    rs.getTimestamp("GioRa"),
                    rs.getString("MaPC")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<chamcong> findByThangNam(String maNV, int thang, int nam) {
        return selectBySql("SELECT * FROM ChamCong WHERE MaNV = ? AND MONTH(GioVao) = ? AND YEAR(GioVao) = ?", maNV, thang, nam);
    }

    public int demSoNgayCong(String maNV, int thang, int nam) {
        int soNgayCong = 0;
      String sql = """
    SELECT COUNT(DISTINCT DATE(Ngay)) 
    FROM ChamCong 
    WHERE MaNV = ? 
      AND MONTH(Ngay) = ? 
      AND YEAR(Ngay) = ? 
      AND GioVao IS NOT NULL 
      AND GioRa IS NOT NULL
""";

        try (Connection conn = connect.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
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

    public boolean insertChamCongThuCong(String maNV, Timestamp gioVao, Timestamp gioRa, String maPC) {
        String sql = "INSERT INTO ChamCong (MaNV, GioVao, GioRa, Ngay, MaPC) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = connect.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maNV);
            ps.setTimestamp(2, gioVao);
            ps.setTimestamp(3, gioRa);
            ps.setDate(4, new java.sql.Date(gioVao.getTime()));
            ps.setString(5, maPC);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}