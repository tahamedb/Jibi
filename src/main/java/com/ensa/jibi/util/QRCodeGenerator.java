package com.ensa.jibi.util;

import com.ensa.jibi.model.Client;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QRCodeGenerator {
    private static final Logger LOGGER = Logger.getLogger(QRCodeGenerator.class.getName());

    public static void generateQRCode(Client client) throws IOException, WriterException {
        String lien =  "http://localhost:4200/";
        String qrCodePath = "D:\\QRCode";
        Path directoryPath = Paths.get(qrCodePath);

        // Ensure directory exists
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        String qrCodeName = qrCodePath + "\\" + client.getFirstname() + client.getId() + "-QRCODE.png";
        System.out.println(client.getId());
        Path qrCodeFilePath = Paths.get(qrCodeName);

        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode("Phone Number:" + client.getPhone() + "\n" +
                "lien:" + lien, BarcodeFormat.QR_CODE, 400, 400);

        // Log the path where the QR code is being saved
        LOGGER.log(Level.INFO, "Saving QR code to: {0}", qrCodeFilePath.toString());

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", qrCodeFilePath);
    }
}
