/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;

import java.sql.Date;
/**
 *
 * @author Admin
 */
public class Suachua {

    public String getMaSC() {
        return maSC;
    }

    public void setMaSC(String maSC) {
        this.maSC = maSC;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getMoTaLoi() {
        return moTaLoi;
    }

    public void setMoTaLoi(String moTaLoi) {
        this.moTaLoi = moTaLoi;
    }

    public double getChiPhi() {
        return chiPhi;
    }

    public void setChiPhi(double chiPhi) {
        this.chiPhi = chiPhi;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public Suachua(String maSC, String maBan, String moTaLoi, double chiPhi, Date ngaySua) {
        this.maSC = maSC;
        this.maBan = maBan;
        this.moTaLoi = moTaLoi;
        this.chiPhi = chiPhi;
        this.ngaySua = ngaySua;
    }

    public Suachua() {
    }
       private String maSC;
    private String maBan;
    private String moTaLoi;
    private double chiPhi;
    private Date ngaySua;
}
