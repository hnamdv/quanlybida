/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Xauth;

import MODEl.Nhanvien;

/**
 *
 * @author Admin
 */
public class phanquyen {
    public static Nhanvien user = null;
    
    public static boolean isLogin(){
        return  user != null ;
    }
    public static boolean check(){
        return isLogin() && "QuanLy". equalsIgnoreCase(user.getChucVu());
    }
    public static void clear(){
        user = null ;
    }
    public class SessionData {
    public static String savedUser = null;
    public static String savedPass = null;
}
}
