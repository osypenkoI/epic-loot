package com.e_commerce.epic_loot.api.controller;

import com.e_commerce.epic_loot.api.DTO.Auth.AuthResponse;
import com.e_commerce.epic_loot.api.DTO.Auth.LoginRequest;
import com.e_commerce.epic_loot.api.DTO.Auth.RefreshTokenRequest;
import com.e_commerce.epic_loot.api.DTO.Auth.RegisterRequest;
import com.e_commerce.epic_loot.api.security.JwtUtil;
import com.e_commerce.epic_loot.enumEntity.AuthorityRole;
import com.e_commerce.epic_loot.service.CustomerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(CustomerService customerService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Авторизация пользователя
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Устанавливаем пользователя в SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Получаем роль пользователя
        AuthorityRole role = customerService.getRoleByUsername(request.getUsername());

        Integer customerId = customerService.getCustomerId(request.getUsername());

        // Генерируем токены
        String accessToken = jwtUtil.generateToken(request.getUsername(), customerId ,role, 1000L * 60 * 15 ); // 15 минут
        String refreshToken = jwtUtil.generateRefreshToken(request.getUsername());

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true) // Только HTTPS
                .path("/")
                .maxAge( 60 * 15) // 15 минут
                .sameSite("None") // Кросс-доменные запросы
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true) // Только HTTPS
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 дней
                .sameSite("None") // Кросс-доменные запросы
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", accessCookie.toString())
                .header("Set-Cookie", refreshCookie.toString())
                .body(Map.of("customerId", customerId));
    }


    /**
     * Обновление Access Token
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        System.out.print("Test1");
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        // Извлекаем refreshToken из куки
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null || !jwtUtil.validateToken(refreshToken, jwtUtil.getREFRESH_SECRET_KEY())) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        String username = jwtUtil.extractUsername(refreshToken, jwtUtil.getREFRESH_SECRET_KEY());
        AuthorityRole role = jwtUtil.extractRole(refreshToken, jwtUtil.getREFRESH_SECRET_KEY());

        // Генерация нового Access Token
        String newAccessToken = jwtUtil.generateToken(username, customerService.getCustomerId(username), role, 1000L * 60 * 15); // 15 минут

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", newAccessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(15 * 60)
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", accessCookie.toString())
                .body("Token refreshed");
    }

    /**
     * Регистрация нового пользователя
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return customerService.registerCustomer(request);
    }
}
