/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LuongService;

import DAO.DaoImple.PhanCongDAO;
import DAO.DaoImple.chamcongdao;
import MODEl.PhanCong;
import MODEl.chamcong;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TinhLuongService {
    private final double HE_SO_TANG_CA = 1.5;
    private chamcongdao chamCongDAO = new chamcongdao();
    private PhanCongDAO phancongDAO = new PhanCongDAO();
    private static final int LUONG_MOT_GIO = 20000;
    
    public double tinhLuongTheoNgay(String maNV, Date ngay) {
        chamcongdao dao = new chamcongdao();
    java.sql.Date sqlDate = new java.sql.Date(ngay.getTime());
chamcong cc = dao.findByNgay(maNV, sqlDate);
        if (cc == null) {
            System.out.println("Chưa chấm công ngày này.");
            return 0;
        }
        Timestamp vao = (Timestamp) cc.getGioVao();
        Timestamp ra = (Timestamp) cc.getGioRa();

        if (vao == null || ra == null) {
            System.out.println("Chưa đầy đủ thời gian chấm công.");
            return 0;
        }

        long millis = ra.getTime() - vao.getTime();
        double hours = millis / (1000.0 * 60 * 60); 

        return hours * LUONG_MOT_GIO;
    }

    public long tinhLuongTheoThang(String maNV, int thang, int nam, int luongMotGio) {
        List<chamcong> danhSach = chamCongDAO.findByThangNam(maNV, thang, nam);
        long tongTien = 0;
        for (chamcong chamCong : danhSach) {
            Timestamp vao = (Timestamp) chamCong.getGioVao();
            Timestamp ra = (Timestamp) chamCong.getGioRa();
            if (vao != null && ra != null) {
                long millis = ra.getTime() - vao.getTime();
                long phutLam = TimeUnit.MILLISECONDS.toMinutes(millis);
                tongTien += (phutLam * luongMotGio) / 60;
            }
        }

        return tongTien;
    }
}

