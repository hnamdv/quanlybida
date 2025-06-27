/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;

/**
 *
 * @author Admin
 */
public class Banbida {

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public String getMaLoaiBan() {
        return maLoaiBan;
    }

    public void setMaLoaiBan(String maLoaiBan) {
        this.maLoaiBan = maLoaiBan;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public double getGiaTheoGio() {
        return giaTheoGio;
    }

    public void setGiaTheoGio(double giaTheoGio) {
        this.giaTheoGio = giaTheoGio;
    }

    public int getTuoiBan() {
        return tuoiBan;
    }

    public void setTuoiBan(int tuoiBan) {
        this.tuoiBan = tuoiBan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Banbida(String maBan, String tenBan, String maLoaiBan, String tinhTrang, double giaTheoGio, int tuoiBan, String ghiChu) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.maLoaiBan = maLoaiBan;
        this.tinhTrang = tinhTrang;
        this.giaTheoGio = giaTheoGio;
        this.tuoiBan = tuoiBan;
        this.ghiChu = ghiChu;
    }

    public Banbida() {
    }
    private String maBan;
    private String tenBan;
    private String maLoaiBan;
    private String tinhTrang;
    private double giaTheoGio;
    private int tuoiBan;
    private String ghiChu;
}
