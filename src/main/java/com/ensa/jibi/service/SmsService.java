package com.ensa.jibi.service;


import com.ensa.jibi.config.TwilioConfig;
import com.ensa.jibi.dto.OtpRequest;
import com.ensa.jibi.dto.OtpResponseDto;
import com.ensa.jibi.dto.OtpStatus;
import com.ensa.jibi.dto.OtpValidationRequest;
import com.ensa.jibi.model.Agent;
import com.ensa.jibi.model.Client;
import com.ensa.jibi.repository.AgentRepository;
import com.ensa.jibi.repository.ClientRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class SmsService {
    @Autowired
    private TwilioConfig twilioConfig;
    @Autowired
      private AgentRepository agentRepository;
    @Autowired
       private ClientRepository clientRepository;
    Map<String, String> otpMap = new HashMap<>();
    public OtpResponseDto sendSMS(OtpRequest otpRequest) {
        OtpResponseDto otpResponseDto = null;
        Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
        try {
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());//to

            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
            String otp = generateOTP();


            String otpMessage = "Dear Customer , Your OTP is  " + otp + " for sending sms through Spring boot application. Thank You.";
            Message message = Message.creator(to, from, otpMessage).create();
            //otpMap.put(otpRequest.getUsername(), otp);//otherwise we need to store it in db
            Optional<Agent> agentOpt = agentRepository.findByUsername(otpRequest.getUsername());
            if(agentOpt.isPresent()){
                Agent agent=agentOpt.get();
                agent.setOtp(otp);
                agentRepository.save(agent);
                otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
            }else {
                otpResponseDto=new OtpResponseDto(OtpStatus.FAILED,"Agent not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }
    public String validateOtp(OtpValidationRequest otpValidationRequest) {
//        Set<String> keys = otpMap.keySet();
//        String username = null;
//        for(String key : keys)
//            username = key;
//        if (otpValidationRequest.getUsername().equals(username)) {
//            otpMap.remove(username,otpValidationRequest.getOtpNumber());
//            return "OTP is valid!";// we can add expiration time
//        } else {
//            return "OTP is invalid!";
//        }
        Optional<Agent> agentOpt = agentRepository.findByUsername(otpValidationRequest.getUsername());
        if (agentOpt.isPresent()) {
            Agent agent = agentOpt.get();
            System.out.println("agent found:"+agent.getUsername()+"with stored otp:"+agent.getOtp());
            if (agent.validateOtp(otpValidationRequest.getOtpNumber())) {
                return "OTP is valid!";
            }
        }
        return "OTP is invalid!";

    }

    private String generateOTP() {
        return new DecimalFormat("000000")//six digit format
                .format(new Random().nextInt(999999));
    }


}


