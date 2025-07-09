/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package img;
import DAO.DaoImple.chamcongdao;
import static spark.Spark.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author Admin
 */
public class anh {
   private static boolean started = false;

   public static void startServer() {
        if (started) return;

        port(8080);

        get("/chamcong", (req, res) -> {
            res.type("text/html; charset=UTF-8");

            String maNV = req.queryParams("maNV");
            if (maNV == null || maNV.isEmpty()) {
                return "<h2 style='color:red;'>❌ Thiếu mã nhân viên!</h2>";
            }

            Timestamp now = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            String gio = sdf.format(now);

            chamcongdao dao = new chamcongdao();
            String message;
            String color;

            if (!dao.daChamCongHomNay(maNV)) {
                dao.insertChamCong(maNV, now);
                message = "✅ Chấm công vào thành công!";
                color = "green";
            } else {
                dao.updateGioRa(maNV, now);
                message = "✅ Chấm công ra thành công!";
                color = "blue";
            }

            return "<!DOCTYPE html>" +
                    "<html><head><meta charset='UTF-8'>" +
                    "<style>" +
                    "body { font-family: Arial; text-align: center; padding: 50px; background: #f0f0f0; }" +
                    ".box { background: white; padding: 30px; border-radius: 10px; display: inline-block; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" +
                    ".status { font-size: 20px; color: " + color + "; }" +
                    ".time { margin-top: 10px; font-size: 16px; color: #555; }" +
                    "</style></head><body>" +
                    "<div class='box'>" +
                    "<h2>Hệ thống chấm công</h2>" +
                    "<p class='status'>" + message + "</p>" +
                    "<p class='time'>Mã nhân viên: <b>" + maNV + "</b></p>" +
                    "<p class='time'>Thời gian: <b>" + gio + "</b></p>" +
                    "</div>" +
                    "</body></html>";
        });

        started = true;
    }
}
