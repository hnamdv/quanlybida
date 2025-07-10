/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DaoImple;

import MODEl.Booking;
import XJDBC.connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author HP
 */
public class BookingDAO {
    public void insert(Booking b) {
        String sql = "INSERT INTO BOOKING (MaBooking, MaBan, TenKhach, SDT, GioDat, GioNhan, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = connect.openConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, b.getMaBooking());
            ps.setString(2, b.getMaBan());
            ps.setString(3, b.getTenKhach());
            ps.setString(4, b.getSdt());
            ps.setTimestamp(5, b.getGioDat());
            ps.setTimestamp(6, b.getGioNhan());
            ps.setString(7, b.getTrangThai());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Booking> getAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM BOOKING";

        try (Connection con = connect.openConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Booking b = new Booking(
                        rs.getString("MaBooking"),
                        rs.getString("MaBan"),
                        rs.getString("TenKhach"),
                        rs.getString("SDT"),
                        rs.getTimestamp("GioDat"),
                        rs.getTimestamp("GioNhan"),
                        rs.getString("TrangThai")
                );
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

public boolean isOverlap(String maBan, Timestamp gioKiemTra) {
    String sql = "SELECT * FROM BOOKING WHERE MaBan = ? AND GioNhan >= ? AND TrangThai = 'ChuaNhan'";
    try (Connection con = connect.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, maBan);
        ps.setTimestamp(2, gioKiemTra);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
public Booking getBookingGanNhat(String maBan) {
    String sql = "SELECT * FROM BOOKING WHERE MaBan = ? ORDER BY GioNhan DESC LIMIT 1";

    try (Connection con = connect.openConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, maBan);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Booking(
                rs.getString("MaBooking"),
                rs.getString("MaBan"),
                rs.getString("TenKhach"),
                rs.getString("SDT"),
                rs.getTimestamp("GioDat"),
                rs.getTimestamp("GioNhan"),
                rs.getString("TrangThai")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}

}
