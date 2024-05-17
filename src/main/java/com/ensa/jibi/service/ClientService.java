package com.ensa.jibi.service;


import com.ensa.jibi.model.Client;
import com.ensa.jibi.repository.ClientRepository;
import com.ensa.jibi.sms.TwilioSmsSender;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;


@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TwilioSmsSender twilioSmsSender;

    public Client registerClient(Client client) {
        // Generate and set temporary password here
        client.setPassword(generateTemporaryPassword());
        // Save client
        twilioSmsSender.sendSms(client.getPhone(), "Your temporary password is: " + client.getPassword());
        return clientRepository.save(client);
    }

    private String generateTemporaryPassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}