package com.socialmeadia.app.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import com.socialmeadia.app.model.QRCode;
import com.socialmeadia.app.repository.QRCodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

@Service
public class QRCodeService {

    @Autowired
    QRCodeRepository qrCodeRepository;
    public BufferedImage createQRCodeImage(String data, int width, int height) throws Exception {
        // Set encoding hints (optional)
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // Create the QR code matrix
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hints);

        // Create the image from the BitMatrix
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        // Draw the QR code
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (matrix.get(i, j)) {
                    image.setRGB(i, j, Color.BLACK.getRGB());
                } else {
                    image.setRGB(i, j, Color.WHITE.getRGB());
                }
            }
        }

        return image;
    }

    @Transactional
    public QRCode generateAndSaveQRCode(String data, int width, int height) throws Exception {
        // Generate QR code
        BufferedImage qrCodeImage = createQRCodeImage(data, width, height);

        // Convert BufferedImage to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrCodeImage, "PNG", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // Create QRCode entity
        QRCode qrCode = new QRCode();
        qrCode.setData(data);
        qrCode.setQrCodeImage(imageBytes);

        // Save QRCode to the database
        return qrCodeRepository.save(qrCode);
    }


    public ResponseEntity<byte[]> getQRCode(Long id){
       QRCode qrCode= qrCodeRepository.findById(id).orElse(null);

        if (qrCode == null) {
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/png")
                .body(qrCode.getQrCodeImage());
    }

}

