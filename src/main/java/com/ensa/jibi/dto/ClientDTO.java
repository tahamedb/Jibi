package com.ensa.jibi.dto;

import com.ensa.jibi.model.AccountType;
import com.ensa.jibi.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ClientDTO {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private AccountType accountType;
    private String cin;

    public ClientDTO(Client client) {
        this.id = client.getId().toString();
        this.firstname = client.getFirstname();
        this.lastname = client.getLastname();
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.accountType = client.getAccountType();
        this.cin = client.getCin();
    }

}
