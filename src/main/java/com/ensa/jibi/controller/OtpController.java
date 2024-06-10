package com.ensa.jibi.controller;

import com.ensa.jibi.dto.OtpRequest;
import com.ensa.jibi.dto.OtpResponseDto;
import com.ensa.jibi.dto.OtpValidationRequest;
import com.ensa.jibi.service.OtpService;
import com.ensa.jibi.service.SmsService;
import com.ensa.jibi.sms.TwilioSmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@RestController
@RequestMapping("/getotp")
@CrossOrigin(origins = "http://localhost:4200/")
@Slf4j
public class OtpController {
    @Autowired
    TwilioSmsSender twilioSmsSender;
    @Autowired
    OtpService otpService;
    @Autowired
    private SmsService smsService;
    @GetMapping("/process")
    public String processSMS() {
        return "SMS sent";
    }

    @PostMapping("/send-otp")
    public OtpResponseDto sendOtp(@RequestBody OtpRequest otpRequest) {
        log.info("inside sendOtp :: "+otpRequest.getUsername());
        return smsService.sendSMS(otpRequest);
    }
    @PostMapping("/validate-otp")
    public String validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
        log.info("inside validateOtp :: "+otpValidationRequest.getUsername()+" "+otpValidationRequest.getOtpNumber());
        return smsService.validateOtp(otpValidationRequest);
    }
    @GetMapping("/generate")
    public ResponseEntity<String> generateOtp(@RequestParam("phoneNumber") String phoneNumber) {
        // Generate a random OTP (e.g., 6-digit code)
        String otp = otpService.generateOtp();
        // Send the OTP to the frontend
        System.out.println(otp);
//        otpService.sendOtp(phoneNumber, otp);

        return ResponseEntity.ok(otp);
    }

}
