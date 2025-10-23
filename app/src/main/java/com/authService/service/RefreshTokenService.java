package com.authService.service;

import com.authService.entities.RefreshToken;
import com.authService.entities.UserInfo;
import com.authService.repository.RefreshTokenRepository;
import com.authService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/*
* Main goal of refresh token service is to :-
* generate the refresh token during login
* return the refresh token instance (row) using findByToken
* check for refresh token expiry
*/

@Service
public class RefreshTokenService {
    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    // constructor dependency injection
    @Autowired
    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String username) throws UsernameNotFoundException {
        // fetch user from db
        Optional<UserInfo> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found. Please sign up.");
        }

        // generate refresh token using the builder pattern
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .userInfo(user.get())
                .build();

        // save the token back into the database
        return refreshTokenRepository.save(refreshToken);
    }

    // retrieve refresh token row using refresh token
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) throws RuntimeException {
        if(token.getExpiryDate().compareTo(Instant.now()) < 0) {
            // expired token
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please login!");
        }

        return token;
    }
}
