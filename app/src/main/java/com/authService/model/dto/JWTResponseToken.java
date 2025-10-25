package com.authService.model.dto;

// dto for the response after signup / login / refreshToken request

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTResponseToken {
    @NotBlank(message = "Access Token is required")
    private String accessToken;

    @NotBlank(message = "Refresh Token is required")
    private String refreshToken;
}
