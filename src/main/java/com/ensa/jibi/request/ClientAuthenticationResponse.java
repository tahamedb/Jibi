package com.ensa.jibi.request;

import com.ensa.jibi.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAuthenticationResponse {
    private String jwt;
    private String role;
    private Client client;
    private boolean isFirstTime;
}