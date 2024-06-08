package com.ensa.jibi.service;

import com.ensa.jibi.sms.TwilioSmsSender;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    TwilioSmsSender sender;


    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }


    public void sendOtp(String toPhoneNumber, String otpCode) {
        sender.sendSms(toPhoneNumber, otpCode);
    }

}
