/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LuongService;

import java.util.Random;

/**
 *
 * @author Admin
 */
public class OTP {
      public static String generateOTP(int length) {
        String chars = "0123456789";
        StringBuilder otp = new StringBuilder();
        Random rnd = new Random();
        while (otp.length() < length) {
            int index = (int) (rnd.nextFloat() * chars.length());
            otp.append(chars.charAt(index));
        }
        return otp.toString();
    }
}
