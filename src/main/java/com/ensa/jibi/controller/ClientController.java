package com.ensa.jibi.controller;

import com.ensa.jibi.dto.ClientDTO;
import com.ensa.jibi.dto.LoginRequestDTO;
import com.ensa.jibi.dto.PasswordChangeDTO;
import com.ensa.jibi.model.AccountType;
import com.ensa.jibi.model.Client;
import com.ensa.jibi.request.AuthenticationRequest;
import com.ensa.jibi.request.AuthenticationResponse;
import com.ensa.jibi.request.ClientAuthenticationResponse;
import com.ensa.jibi.request.ClientRequest;
import com.ensa.jibi.service.AgentDetailsService;
import com.ensa.jibi.service.ClientService;
import com.ensa.jibi.util.JwtUtil;
import com.ensa.jibi.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:4200/")
public class ClientController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AgentDetailsService agentDetailsService;

    @Autowired
    private ClientService clientService;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody ClientDTO clientDTO) {
        System.out.println(clientDTO);
        try {
            Client client = new Client();
            client.setFirstname(clientDTO.getFirstname());
            client.setLastname(clientDTO.getLastname());
            client.setEmail(clientDTO.getEmail());
            client.setPhone(clientDTO.getPhone());
            client.setAccountType(clientDTO.getAccountType());
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
    @GetMapping("/getbalance")
    public ResponseEntity<Float> getbalance(@RequestParam String phoneNumber) {

        System.out.println(phoneNumber);
        Float balance = clientService.getbalancebyPhone(phoneNumber);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        System.out.println(loginRequest);
        Optional<Client> clientOpt = clientService.verifyPassword(loginRequest.getPhone(), loginRequest.getPassword());
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            boolean isFirstTime = client.isRequiresPasswordChange(); // Assuming this field indicates first-time login
            // Additional logic to handle other cases if needed

            // Return response with isFirstTime flag
            return ResponseEntity.ok(Map.of("client", client, "isFirstTime", isFirstTime));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid phone number or password");
        }
    }
    @PostMapping("/authenticate_client")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody ClientRequest clientRequest) throws Exception {
        System.out.println("Received authentication request for user: " + clientRequest.getPhone());

        // Authenticate the client
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(clientRequest.getPhone(), clientRequest.getPassword())
            );
            System.out.println("Authentication successful for user: " + clientRequest.getPhone());
        } catch (Exception e) {
            System.out.println("Authentication failed for user: " + clientRequest.getPhone());
            throw new Exception("Incorrect username or password", e);
        }

        // Load user details
        UserDetails userDetails = agentDetailsService.loadUserByUsername(clientRequest.getPhone());

        // Generate JWT token
        String jwt = jwtUtil.generateToken(userDetails);
        System.out.println("Generated JWT: " + jwt);

        // Determine role
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        // Find client and check if it's their first time
        Optional<Client> clientOpt = clientService.findByPhone(clientRequest.getPhone());
        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            boolean isFirstTime = client.isRequiresPasswordChange(); // Assuming this field indicates first-time login

            // Return response including client details, JWT, and isFirstTime flag
            ClientAuthenticationResponse response = new ClientAuthenticationResponse(jwt, role, client, isFirstTime);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Client not found");
        }
    }



    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordChangeRequest) {
        System.out.println(passwordChangeRequest);
        System.out.println(passwordChangeRequest);
        if (!passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getConfirmNewPassword())) {
            return ResponseEntity.badRequest().body("New password and confirm password do not match");
        }

        try {
            boolean success = clientService.changePassword(passwordChangeRequest.getPhone(), passwordChangeRequest.getCurrentPassword(), passwordChangeRequest.getNewPassword());
            if (success) {
                return ResponseEntity.ok(Collections.singletonMap("message", "Password changed successfully"));
            } else {
                return ResponseEntity.status(400).body("Failed to change password. Current password might be incorrect.");
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
    @GetMapping("/getClientByPhoneNumber")
    public ResponseEntity<ClientDTO> getClientByPhoneNumber(@RequestParam String phoneNumber) {
        System.out.println(phoneNumber);
        ClientDTO clientDTO = clientService.getClientByPhoneNumber(phoneNumber);
        if (clientDTO != null) {
            return ResponseEntity.ok(clientDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}