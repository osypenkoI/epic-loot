package com.e_commerce.epic_loot.api.DTO.Auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
