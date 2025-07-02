/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package XJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect {
    private static Connection connection = null;

    public static Connection openConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String dburl = "jdbc:mysql://localhost:3306/QuanLybida?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "admin";
        String password = "12345";

        try {
            if (!isReady()) {
                Class.forName(driver);
                connection = DriverManager.getConnection(dburl, username, password);
                System.out.println("Kết nối thành công.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Lỗi kết nối CSDL: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (isReady()) {
                connection.close();
                System.out.println("Đã đóng kết nối.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static boolean isReady() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
