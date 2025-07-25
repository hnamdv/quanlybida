/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LuongService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
/**
 *
 * @author Admin
 */
public class Xstr {
    
    public static boolean isBlank(String text) {
        return text == null || text.trim().length() == 0;
    }

    public static String valueOf(Object object) {
        return object == null ? "" : String.valueOf(object);
    }

    public static String encodeB64(String text) {
        byte[] data = text.getBytes();
        return Base64.getEncoder().encodeToString(data);
    }

    public static String decodeB64(String text) {
        byte[] data = Base64.getDecoder().decode(text);
        return new String(data);
    }

    public static String getKey(String... args) {
        if (args.length == 0) {
            args = new String[]{UUID.randomUUID().toString(), String.valueOf(System.currentTimeMillis())};
        }
        int hashCode = String.join("-", args).hashCode();
        String key = "XXXXXXXX" + Integer.toHexString(Math.abs(hashCode)).toUpperCase();
        return key.substring(key.length() - 8);
    }

    public static void main(String[] args) {
      
    }
public static String hashSHA256(String text) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b)); 
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
}

}
