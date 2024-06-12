package com.ensa.jibi.controller;


import com.ensa.jibi.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/qr")
@CrossOrigin( origins ="http://localhost:4200")

public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;


    @PostMapping("/scan")
    public ResponseEntity<String> scanQRCode(@RequestParam("file") MultipartFile file) {
        try {
            // Enregistrer le fichier téléchargé dans un emplacement temporaire
            File tempFile = File.createTempFile("uploaded-", file.getOriginalFilename());
            file.transferTo(tempFile);

            // Décoder le QR code
            String decodedText = qrCodeService.decodeQRCode(tempFile);

            // Supprimer le fichier temporaire
            tempFile.delete();

            if (decodedText == null) {
                return ResponseEntity.badRequest().body("No QR code found in the image.");
            } else {
                return ResponseEntity.ok(decodedText);
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("An error occurred while processing the QR code.");
        }
    }
}
