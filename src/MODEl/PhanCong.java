/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Admin
 */
public class PhanCong {
        private String maPC;
    private String maNV;
    private String tenCa;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private LocalDate ngayLam;
    private double heSoLuong;
      public PhanCong() {}

    public PhanCong(String maPC, String maNV, String tenCa, LocalTime gioBatDau, LocalTime gioKetThuc, LocalDate ngayLam, double heSoLuong) {
        this.maPC = maPC;
        this.maNV = maNV;
        this.tenCa = tenCa;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.ngayLam = ngayLam;
        this.heSoLuong = heSoLuong;
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

    public String getTenCa() {
        return tenCa;
    }

    public void setTenCa(String tenCa) {
        this.tenCa = tenCa;
    }

    public LocalTime getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(LocalTime gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public LocalTime getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(LocalTime gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
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
}

