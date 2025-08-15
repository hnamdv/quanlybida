/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;

/**
 *
 * @author tamng
 */
public class ChiTietBooking {
    private String maCTBooking;
    private String maBooking;
    private String maBan;
    private String gioBatDau;
    private String gioKetThuc;
    private String ghiChu;

    public ChiTietBooking(String maCTBooking, String maBooking, String maBan, String gioBatDau, String gioKetThuc, String ghiChu) {
        this.maCTBooking = maCTBooking;
        this.maBooking = maBooking;
        this.maBan = maBan;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.ghiChu = ghiChu;
    }

    // Getter và Setter
    public String getMaCTBooking() {
        return maCTBooking;
    }

    public void setMaCTBooking(String maCTBooking) {
        this.maCTBooking = maCTBooking;
    }

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

    public String getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public String getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(String gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}

