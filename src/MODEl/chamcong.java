/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class chamcong {

    public int getMaCC() {
        return maCC;
    }

    public void setMaCC(int maCC) {
        this.maCC = maCC;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public Date getGioVao() {
        return gioVao;
    }

    public void setGioVao(Date gioVao) {
        this.gioVao = gioVao;
    }

    public Date getGioRa() {
        return gioRa;
    }

    public void setGioRa(Date gioRa) {
        this.gioRa = gioRa;
    }

    public chamcong(int maCC, String maNV, Date gioVao, Date gioRa) {
        this.maCC = maCC;
        this.maNV = maNV;
        this.gioVao = gioVao;
        this.gioRa = gioRa;
    }

    public chamcong() {
    }
      private int maCC;
    private String maNV;
    private Date gioVao;
    private Date gioRa;

}
