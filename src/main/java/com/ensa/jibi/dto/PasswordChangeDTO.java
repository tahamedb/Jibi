package com.ensa.jibi.dto;

import lombok.Data;

@Data
public class PasswordChangeDTO {
    private String phone;
    private String newPassword;
    private String currentPassword;
    private String confirmNewPassword;

}
