package com.e_commerce.epic_loot.api.DTO.Auth;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}