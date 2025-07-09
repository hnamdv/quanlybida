/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;

/**
 *
 * @author Admin
 */
import java.sql.Date;
import java.sql.Timestamp;

public class Hoadon {

    public Hoadon(String maHD, String maNV, String maBan, Timestamp thoiGianBatDau, Timestamp thoiGianKetThuc, double tongTien, String trangThai, Date ngayTao, double tienGio, double giamGia, double tienDV, String ghiChu) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.maBan = maBan;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.tienGio = tienGio;
        this.giamGia = giamGia;
        this.tienDV = tienDV;
        this.ghiChu = ghiChu;
    }

    public Hoadon() {
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public Timestamp getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Timestamp thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Timestamp getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Timestamp thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getTienGio() {
        return tienGio;
    }

    public void setTienGio(double tienGio) {
        this.tienGio = tienGio;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getTienDV() {
        return tienDV;
    }

    public void setTienDV(double tienDV) {
        this.tienDV = tienDV;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    private String maHD;
    private String maNV;
    private String maBan;
    private Timestamp thoiGianBatDau;
    private Timestamp thoiGianKetThuc;
    private double tongTien;
    private String trangThai;
    private Date ngayTao;
    private double tienGio;
    private double giamGia;
    private double tienDV;
    private String ghiChu;

}
