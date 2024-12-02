package com.e_commerce.epic_loot.api.DTO.Auth;

import lombok.Data;

@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;

    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public AuthResponse() {
        this.accessToken = "";
        this.refreshToken = "";
    }
}
