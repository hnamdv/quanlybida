package DAO.DaoImple;

import MODEl.Dichvu;
import XJDBC.connect;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DichVuDAO {

    public List<Dichvu> getDichVuByHoaDon(String maHD) {
        List<Dichvu> list = new ArrayList<>();
        String sql = "SELECT dv.MaDV, dv.TenDV, ctdv.SoLuong, dv.DonGia "
                + "FROM ct_hoadon_dichvu ctdv "
                + "JOIN dichvu dv ON ctdv.MaDV = dv.MaDV "
                + "WHERE ctdv.MaHD = ?";

        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Dichvu dv = new Dichvu();
                dv.setMaDV(rs.getString("MaDV"));
                dv.setTenDV(rs.getString("TenDV"));
                dv.setDonGia(rs.getDouble("DonGia"));
                list.add(dv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✔ CREATE - Thêm dịch vụ
    public boolean insert(Dichvu dv) {
        String sql = "INSERT INTO DICHVU (MaDV, TenDV, DonGia) VALUES (?, ?, ?)";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dv.getMaDV());
            ps.setString(2, dv.getTenDV());
            ps.setDouble(3, dv.getDonGia());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✔ READ - Lấy tất cả dịch vụ
    public List<Dichvu> getAll() {
        List<Dichvu> list = new ArrayList<>();
        String sql = "SELECT * FROM DICHVU";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Dichvu dv = new Dichvu();
                dv.setMaDV(rs.getString("MaDV"));
                dv.setTenDV(rs.getString("TenDV"));
                dv.setDonGia(rs.getDouble("DonGia"));
                list.add(dv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✔ UPDATE - Cập nhật dịch vụ
    public boolean update(Dichvu dv) {
        String sql = "UPDATE DICHVU SET TenDV = ?, DonGia = ? WHERE MaDV = ?";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dv.getTenDV());
            ps.setDouble(2, dv.getDonGia());
            ps.setString(3, dv.getMaDV());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✔ DELETE - Xoá dịch vụ
    public boolean delete(String maDV) {
        String sql = "DELETE FROM DICHVU WHERE MaDV = ?";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✔ FIND - Tìm theo mã
    public Dichvu findByMaDV(String maDV) {
        String sql = "SELECT * FROM DICHVU WHERE MaDV = ?";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Dichvu(
                            rs.getString("MaDV"),
                            rs.getString("TenDV"),
                            rs.getDouble("DonGia"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Dichvu> getDichVuTheoHoaDon(String maHD) {
        List<Dichvu> list = new ArrayList<>();
        String sql = "SELECT dv.TenDV, ctdv.SoLuong, dv.DonGia FROM CT_HOADON_DICHVU ctdv "
                + "JOIN DICHVU dv ON ctdv.MaDV = dv.MaDV WHERE ctdv.MaHD = ?";
        try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Dichvu dv = new Dichvu();
                dv.setTenDV(rs.getString("TenDV"));
                dv.setDonGia(rs.getDouble("DonGia"));
                list.add(dv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Dichvu getByTen(String tenDV) {
        String sql = "SELECT * FROM dichvu WHERE TenDV = ?";
        try (Connection conn = connect.openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Dichvu(
                        rs.getString("MaDV"),
                        rs.getString("TenDV"),
                        rs.getDouble("DonGia")
                );

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
public List<Dichvu> selectAll() {
    List<Dichvu> list = new ArrayList<>();
    String sql = "SELECT * FROM DICHVU";
    try (Connection con = connect.openConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            Dichvu dv = new Dichvu();
            dv.setMaDV(rs.getString("MaDV"));
            dv.setTenDV(rs.getString("TenDV"));
            dv.setDonGia(rs.getDouble("DonGia"));
            list.add(dv);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
