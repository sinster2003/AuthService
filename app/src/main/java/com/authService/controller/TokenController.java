package com.authService.controller;

import com.authService.model.dto.AuthRequestDTO;
import com.authService.model.dto.JWTResponseDTO;
import com.authService.model.dto.RefreshTokenDTO;
import com.authService.model.entity.RefreshToken;
import com.authService.service.JWTService;
import com.authService.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class TokenController {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JWTService jwtService;

    @Autowired
    public TokenController(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("auth/v1/login")
    public ResponseEntity<Object> loginUser(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );

            if (authentication.isAuthenticated()) {
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
                String accessToken = jwtService.generateToken(new HashMap<>(), authRequestDTO.getUsername());
                return new ResponseEntity<>(
                        JWTResponseDTO
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getToken())
                        .build(),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>("Invalid credentials. Could not authenticate user", HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>("Something went wrong " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("auth/v1/refreshtoken")
    public JWTResponseDTO refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        return refreshTokenService.findByToken(refreshTokenDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map((userInfo) -> {
                    String accessToken = jwtService.createToken(new HashMap<>(), userInfo.getUsername());
                    return JWTResponseDTO
                            .builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenDTO.getToken())
                            .build();
                })
                .orElseThrow(() -> new RuntimeException("Refresh Token is present in DB"));
    }
}
