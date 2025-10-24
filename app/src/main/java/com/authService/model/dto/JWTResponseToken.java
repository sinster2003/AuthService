package com.authService.model.dto;

// dto for the response after signup / login / refreshToken request

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTResponseToken {
    private String accessToken;
    private String refreshToken;
}
