package com.ensa.jibi.controller;


import com.ensa.jibi.dto.OtpValidationRequest;
import com.ensa.jibi.model.Agent;
import com.ensa.jibi.model.BackOffice;
import com.ensa.jibi.request.ChangePasswordRequest;
import com.ensa.jibi.service.BackOfficeService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/backoffice")
@CrossOrigin(origins = "http://localhost:4200")
public class BackofficeController {
    @Autowired
    private BackOfficeService backOfficeService;
    @PostMapping(value = "/addAgent",consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<?> addAgent(@ModelAttribute Agent newAgent, @RequestPart("nomfichier") MultipartFile cinPdfFile ) throws Exception {
//        backOfficeService.addAgent(newAgent);
//        return ResponseEntity.ok().build();
        System.out.println("sbaxi land");
        try {
          String username= backOfficeService.addAgent(newAgent, cinPdfFile );
            return ResponseEntity.ok().body("\"Agent added successfully\"");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding agent: " + e.getMessage());
        }
    }

    @GetMapping("/getBackOffice/{username}")
    public ResponseEntity<BackOffice> getBackOffice(@PathVariable String username) {
        BackOffice backOffice = backOfficeService.getBackOffice(username);
        return ResponseEntity.ok(backOffice);
    }

    @PostMapping("/saveAdmin")
    public ResponseEntity<BackOffice> saveAdmin(@RequestBody BackOffice backOffice) {
        BackOffice savedBackOffice = backOfficeService.saveAdmin(backOffice);
        return ResponseEntity.ok(savedBackOffice);
    }
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        try {
            backOfficeService.sendOtpForPasswordReset(email);
            return ResponseEntity.ok("OTP envoyé avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<Map<String,String>> validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        System.out.println("received otp request"+otpValidationRequest);
        boolean isValid = backOfficeService.validateOtp(otpValidationRequest.getUsername(), otpValidationRequest.getOtpNumber());
        if (isValid) {
           // return ResponseEntity.ok("OTP validé avec succès");
            return ResponseEntity.ok(Collections.singletonMap("message", "OTP validé avec succès"));
        } else {
            return ResponseEntity.status(400).body(Collections.singletonMap("error", "OTP invalide"));
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            backOfficeService.changePassword(request.getEmail(), request.getNewPassword());
            return ResponseEntity.ok("\"password changed successfully\"");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
