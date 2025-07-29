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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import img.anh;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
  
    jPanelQR.setLayout(new GridBagLayout()); 
    jPanelQR.setPreferredSize(new Dimension(300, 300));
    jPanelQR.setBorder(BorderFactory.createTitledBorder("Mã QR Chấm Công")); 

    anh.startServer();

    
    SwingUtilities.invokeLater(() -> {
        showQR();
    });

    }
    private String maNV;

        private void showQR() {
        try {
            String maNV = phanquyen.user.getMaNV();
            String qrContent = "maNV=" + maNV;


            BufferedImage qrImage = generateQRImage(qrContent, 250, 250);

            JLabel label = new JLabel(new ImageIcon(qrImage));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);

            jPanelQR.removeAll();

            // Dùng GridBagLayout để căn giữa
            jPanelQR.setLayout(new GridBagLayout());
            jPanelQR.add(label, new GridBagConstraints());

            jPanelQR.revalidate();
            jPanelQR.repaint();
            JLabel nameLabel = new JLabel("Nhân viên: " + phanquyen.user.getHoTen());
    nameLabel.setHorizontalAlignment(JLabel.CENTER);
    nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    nameLabel.setForeground(Color.DARK_GRAY);

    // Panel tạm chứa QR + tên
    JPanel panelWrapper = new JPanel(new BorderLayout());
    panelWrapper.setOpaque(false); // không đè màu
    panelWrapper.add(label, BorderLayout.CENTER);
    panelWrapper.add(nameLabel, BorderLayout.SOUTH);

    jPanelQR.add(panelWrapper, new GridBagConstraints());

        } catch (Exception e) {
            e.printStackTrace();
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
        private void saveQRToPDF() {
    try {
        String maNV = phanquyen.user.getMaNV();
        String qrContent = "maNV=" + maNV;
        BufferedImage qrImage = generateQRImage(qrContent, 250, 250);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu mã QR");
        fileChooser.setSelectedFile(new File("QR_" + maNV + ".pdf"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            baos.flush();
            Image qrItextImage = Image.getInstance(baos.toByteArray());
            baos.close();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
            document.open();

            document.add(new Paragraph("Mã QR chấm công cho nhân viên: " + phanquyen.user.getHoTen()));
            document.add(new Paragraph("Mã NV: " + maNV));
            document.add(Chunk.NEWLINE);
            qrItextImage.scaleToFit(200, 200);
            qrItextImage.setAlignment(Element.ALIGN_CENTER);
            document.add(qrItextImage);

            document.close();
            JOptionPane.showMessageDialog(this, "✅ Đã lưu QR vào: " + fileToSave.getAbsolutePath());
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Lỗi khi lưu mã QR");
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelQR = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanelQR.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelQRLayout = new javax.swing.GroupLayout(jPanelQR);
        jPanelQR.setLayout(jPanelQRLayout);
        jPanelQRLayout.setHorizontalGroup(
            jPanelQRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelQRLayout.setVerticalGroup(
            jPanelQRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 759, Short.MAX_VALUE)
        );

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Tính Lương");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 102, 102));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Lịch Sử");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnsave.setBackground(new java.awt.Color(0, 102, 102));
        btnsave.setForeground(new java.awt.Color(255, 255, 255));
        btnsave.setText("Save QR");
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 102, 102));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Thủ Công");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jButton1)
                .addGap(45, 45, 45)
                .addComponent(jButton2)
                .addGap(52, 52, 52)
                .addComponent(jButton3)
                .addGap(43, 43, 43)
                .addComponent(btnsave)
                .addContainerGap(731, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(btnsave)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jPanelQR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                           
        NgayLuongNhanVien form = new NgayLuongNhanVien();
form.setVisible(true); // Lúc này cửa sổ form sẽ bật lên
                                                                                                     // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                           lichsu form = new lichsu();
form.setVisible(true); // Lúc này cửa sổ form sẽ bật lên
                   // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        // TODO add your handling code here:
        saveQRToPDF();
        
    }//GEN-LAST:event_btnsaveActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
                                
        ThuCong form = new ThuCong();
form.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnsave;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanelQR;
    // End of variables declaration//GEN-END:variables
}
