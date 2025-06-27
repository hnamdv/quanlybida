/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;
import java.sql.Timestamp;
/**
 *
 * @author Admin
 */
public class Booking {

    public String getMaBooking() {
        return maBooking;
    }

    public void setMaBooking(String maBooking) {
        this.maBooking = maBooking;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getTenKhach() {
        return tenKhach;
    }

    public void setTenKhach(String tenKhach) {
        this.tenKhach = tenKhach;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Timestamp getGioDat() {
        return gioDat;
    }

    public void setGioDat(Timestamp gioDat) {
        this.gioDat = gioDat;
    }

    public Timestamp getGioNhan() {
        return gioNhan;
    }

    public void setGioNhan(Timestamp gioNhan) {
        this.gioNhan = gioNhan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Booking(String maBooking, String maBan, String tenKhach, String sdt, Timestamp gioDat, Timestamp gioNhan, String trangThai) {
        this.maBooking = maBooking;
        this.maBan = maBan;
        this.tenKhach = tenKhach;
        this.sdt = sdt;
        this.gioDat = gioDat;
        this.gioNhan = gioNhan;
        this.trangThai = trangThai;
    }

    public Booking() {
    }
    private String maBooking;
    private String maBan;
    private String tenKhach;
    private String sdt;
    private Timestamp gioDat;
    private Timestamp gioNhan;
    private String trangThai;
}
