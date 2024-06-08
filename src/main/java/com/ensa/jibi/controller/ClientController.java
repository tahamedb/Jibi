package com.ensa.jibi.controller;

import com.ensa.jibi.dto.ClientDTO;
import com.ensa.jibi.dto.LoginRequestDTO;
import com.ensa.jibi.dto.PasswordChangeDTO;
import com.ensa.jibi.model.AccountType;
import com.ensa.jibi.model.Client;
import com.ensa.jibi.service.ClientService;
import com.ensa.jibi.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200/")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody ClientDTO clientDTO) {
        try {
            Client client = new Client();
            client.setFirstname(clientDTO.getFirstname());
            client.setLastname(clientDTO.getLastname());
            client.setEmail(clientDTO.getEmail());
            client.setPhone(clientDTO.getPhone());
            client.setAccountType(AccountType.valueOf(clientDTO.getAccountType()));
            client.setCin(clientDTO.getCin());


            Client registeredClient = clientService.registerClient(client);
            QRCodeGenerator.generateQRCode(client);

            return ResponseEntity.ok(registeredClient);
        }


        catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
            }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        Optional<Client> clientOpt = clientService.verifyPassword(loginRequest.getPhone(), loginRequest.getPassword());
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(401).body("Invalid phone number or password");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordChangeRequest) {
        boolean success = clientService.changePassword(passwordChangeRequest.getPhone(), passwordChangeRequest.getNewPassword());
        if (success) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to change password");
        }
    }
    @GetMapping("/getClientByPhoneNumber")
    public ResponseEntity<ClientDTO> getClientByPhoneNumber(@RequestParam String phoneNumber) {
        ClientDTO clientDTO = clientService.getClientByPhoneNumber(phoneNumber);
        if (clientDTO != null) {
            return ResponseEntity.ok(clientDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}