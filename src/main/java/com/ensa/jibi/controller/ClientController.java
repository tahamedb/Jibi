package com.ensa.jibi.controller;

import com.ensa.jibi.dto.ClientDTO;
import com.ensa.jibi.model.Client;
import com.ensa.jibi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @PostMapping("/register")
    public ResponseEntity<Client> registerClient(@RequestBody ClientDTO clientDTO) {
        Client client = new Client();
        client.setFirstname(clientDTO.getFirstname());
        client.setLastname(clientDTO.getLastname());
        client.setEmail(clientDTO.getEmail());
        client.setPhone(clientDTO.getPhone());
        client.setCin(clientDTO.getCin());

        client.setAccountType(clientDTO.getAccountType());

        Client registeredClient = clientService.registerClient(client);


        return ResponseEntity.ok(registeredClient);
    }
}