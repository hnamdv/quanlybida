/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;

/**
 *
 * @author Admin
 */
public class Chitiethoadon {

    public String getMaCT() {
        return maCT;
    }

    public void setMaCT(String maCT) {
        this.maCT = maCT;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public Chitiethoadon(String maCT, String maHD, String maDV, int soLuong, double donGia) {
        this.maCT = maCT;
        this.maHD = maHD;
        this.maDV = maDV;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public Chitiethoadon() {
    }
    private String maCT;
    private String maHD;
    private String maDV;
    private int soLuong;
    private double donGia;
}
