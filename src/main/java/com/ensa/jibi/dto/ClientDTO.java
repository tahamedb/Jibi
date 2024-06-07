package com.ensa.jibi.dto;

import com.ensa.jibi.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ClientDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private AccountType accountType;
    private String cin;




}
