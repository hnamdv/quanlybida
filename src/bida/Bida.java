/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import XJDBC.connect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Admin
 */
public class Bida {

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) {
              String sql = "Select * from NHANVIEN ";
            try (Connection con = connect.openConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

           
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
