package com.e_commerce.epic_loot.api.DTO.Auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    private String phoneNumber;

}
