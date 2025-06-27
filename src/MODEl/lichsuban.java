/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEl;

/**
 *
 * @author Admin
 */
public class lichsuban {

    public String getMaLS() {
        return maLS;
    }

    public void setMaLS(String maLS) {
        this.maLS = maLS;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public lichsuban(String maLS, String maBan, String maHD) {
        this.maLS = maLS;
        this.maBan = maBan;
        this.maHD = maHD;
    }

    public lichsuban() {
    }
        private String maLS;
    private String maBan;
    private String maHD;
}
