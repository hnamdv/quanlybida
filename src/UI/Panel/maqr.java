/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Panel;

import Xauth.phanquyen;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import img.anh;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Admin
 */
public class maqr extends javax.swing.JPanel {

    /**
     * Creates new form maqr
     */
    public maqr() {
        initComponents();
         setPreferredSize(new Dimension(300, 300));
        setLayout(new BorderLayout());
        anh.startServer();
        showQR();
    
    }
    private String maNV;
private void showQR() {
    
    try {
        if (phanquyen.user == null) {
            add(new JLabel("Không có dữ liệu người dùng"));
            return;
        }
        String maNV = phanquyen.user.getMaNV();
        if (maNV == null || maNV.trim().isEmpty()) {
            add(new JLabel("Mã nhân viên rỗng"));
            return;
        }
  String qrContent = "https://ff68-42-117-147-170.ngrok-free.app/chamcong?maNV=" + maNV;
        BufferedImage qrImage = generateQRImage(qrContent, 250, 250);
        JLabel label = new JLabel(new ImageIcon(qrImage));
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    } catch (Exception e) {
        e.printStackTrace();
        add(new JLabel("Lỗi tạo mã QR"));
    }
}


    private BufferedImage generateQRImage(String text, int width, int height) throws WriterException {
        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix matrix = new MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                width,
                height,
                hintMap
        );

        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
