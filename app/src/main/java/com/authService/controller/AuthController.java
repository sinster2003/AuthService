package com.authService.controller;

/*
    Handles signup requests
*/

import com.authService.model.dto.JWTResponseDTO;
import com.authService.model.dto.UserInfoDTO;
import com.authService.model.entity.RefreshToken;
import com.authService.service.JWTService;
import com.authService.service.RefreshTokenService;
import com.authService.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class AuthController {
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final JWTService jwtService;

    @Autowired
    public AuthController(UserDetailsServiceImpl userDetailsService, RefreshTokenService refreshTokenService, JWTService jwtService) {
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("auth/v1/signup")
    public ResponseEntity<Object> SignUp(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            Boolean isSignedUp = userDetailsService.signupUser(userInfoDTO);

            if(Boolean.FALSE.equals(isSignedUp)) {
                return new ResponseEntity<>("User already exists. Please Sign Up", HttpStatus.BAD_REQUEST);
            }

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDTO.getUsername());
            String jwtToken = jwtService.generateToken(new HashMap<>(), userInfoDTO.getUsername());

            return new ResponseEntity<>(
                    JWTResponseDTO.builder()
                    .refreshToken(refreshToken.getToken())
                    .accessToken(jwtToken)
                    .build(),
                    HttpStatus.OK
            );
        }
        catch(Exception ex) {
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
