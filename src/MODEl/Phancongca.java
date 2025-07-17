/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;

import java.time.LocalDate;

/**
 *
 * @author Admin
 */
public class Phancongca {

    public Phancongca(String maPC, String maNV, String maCa, LocalDate ngayLam, double heSoLuong, double luongThuong) {
        this.maPC = maPC;
        this.maNV = maNV;
        this.maCa = maCa;
        this.ngayLam = ngayLam;
        this.heSoLuong = heSoLuong;
        this.luongThuong = luongThuong;
    }

    public String getMaPC() {
        return maPC;
    }

    public void setMaPC(String maPC) {
        this.maPC = maPC;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }

    public LocalDate getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(LocalDate ngayLam) {
        this.ngayLam = ngayLam;
    }

    public double getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(double heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    public double getLuongThuong() {
        return luongThuong;
    }

    public void setLuongThuong(double luongThuong) {
        this.luongThuong = luongThuong;
    }

    public Phancongca() {
    }
        private String maPC;
    private String maNV;
    private String maCa;
    private LocalDate ngayLam;
    private double heSoLuong;
    private double luongThuong;

}
