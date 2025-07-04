/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package img;
import DAO.DaoImple.chamcongdao;
import static spark.Spark.*;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class anh {
   private static boolean started = false;

    public static void startServer() {
        if (started) return; // 1 lan 
        port(8080);
        get("/chamcong", (req, res) -> {
            String maNV = req.queryParams("maNV");
            if (maNV == null || maNV.isEmpty()) return "Thiếu mã nhân viên";
            Timestamp now = new Timestamp(System.currentTimeMillis());
            chamcongdao dao = new chamcongdao();
            if (!dao.daChamCongHomNay(maNV)) {
                dao.insertChamCong(maNV, now);
                return "✅ Chấm công vào thành công!";
            } else {
                dao.updateGioRa(maNV, now);
                return "✅ Chấm công ra thành công!";
            }
        });
        started = true;
    }
}
