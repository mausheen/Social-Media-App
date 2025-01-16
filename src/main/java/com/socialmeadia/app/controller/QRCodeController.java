package com.socialmeadia.app.controller;


import com.socialmeadia.app.model.QRCode;
import com.socialmeadia.app.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

    @Controller
    public class QRCodeController {

        @Autowired
        private QRCodeService qrCodeService;

        @GetMapping("/generate-qrcode")
        public ResponseEntity<byte[]> generateQRCode(@RequestParam String data) throws Exception {
            BufferedImage qrCodeImage = qrCodeService.createQRCodeImage(data, 200, 200);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "PNG", byteArrayOutputStream);

            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/png")
                    .body(imageBytes);
        }



        @GetMapping("/generate-and-save-qrcode")
        public ResponseEntity<String> generateAndSaveQRCode(@RequestParam String data) {
            try {
                // Generate and save the QR code to the database
                QRCode qrCode = qrCodeService.generateAndSaveQRCode(data, 300, 300);

                return ResponseEntity.ok("QR Code saved successfully with ID: " + qrCode.getId());
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error generating and saving QR Code");
            }
        }

        @GetMapping("/get-qrcode/{id}")
        public ResponseEntity<byte[]> getQRCode(@PathVariable Long id) {
            return qrCodeService.getQRCode(id);

        }
    }



