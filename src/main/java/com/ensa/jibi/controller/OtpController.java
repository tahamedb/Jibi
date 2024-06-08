package com.ensa.jibi.controller;

import com.ensa.jibi.service.OtpService;
import com.ensa.jibi.sms.TwilioSmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/getotp")
@CrossOrigin(origins = "http://localhost:4200/")

public class OtpController {
    @Autowired
    TwilioSmsSender twilioSmsSender;
    @Autowired
    OtpService otpService;

    @GetMapping("/generate")
    public ResponseEntity<String> generateOtp(@RequestParam("phoneNumber") String phoneNumber) {
        // Generate a random OTP (e.g., 6-digit code)
        String otp = otpService.generateOtp();
        // Send the OTP to the frontend
        otpService.sendOtp(phoneNumber, otp);
        return ResponseEntity.ok(otp);
    }

}
