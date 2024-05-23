package com.ensa.jibi.service;

import com.ensa.jibi.cmi.CmiService;
import com.ensa.jibi.model.Client;
import com.ensa.jibi.repository.ClientRepository;
import com.ensa.jibi.sms.TwilioSmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TwilioSmsSender twilioSmsSender;

    @Autowired
    private CmiService cmiService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Client registerClient(Client client) {
        if (!cmiService.isResponseFavorable(client)) {
            throw new IllegalArgumentException("CMI response is not favorable");
        }
        String tempPassword = generateTemporaryPassword();
        client.setPassword(passwordEncoder.encode(tempPassword));  // Hashing du mot de passe temporaire
        twilioSmsSender.sendSms(client.getPhone(), "Your temporary password is: " + tempPassword);
        return clientRepository.save(client);
    }

    public Optional<Client> verifyPassword(String phone, String password) {
        Optional<Client> clientOpt = clientRepository.findByPhone(phone);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            if (passwordEncoder.matches(password, client.getPassword())) {
                return Optional.of(client);
            }
        }
        return Optional.empty();
    }

    public boolean changePassword(String phone, String newPassword) {
        Optional<Client> clientOpt = clientRepository.findByPhone(phone);
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            client.setPassword(passwordEncoder.encode(newPassword));  // Hashing du nouveau mot de passe
            client.setRequiresPasswordChange(false);
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    private String generateTemporaryPassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}
