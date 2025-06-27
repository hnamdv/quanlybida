/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;

/**
 *
 * @author Admin
 */
public class Loaiban {

    public String getMaLoaiBan() {
        return maLoaiBan;
    }

    public void setMaLoaiBan(String maLoaiBan) {
        this.maLoaiBan = maLoaiBan;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Loaiban(String maLoaiBan, String tenLoai, String moTa) {
        this.maLoaiBan = maLoaiBan;
        this.tenLoai = tenLoai;
        this.moTa = moTa;
    }

    public Loaiban() {
    }
        private String maLoaiBan;
    private String tenLoai;
    private String moTa;
}
