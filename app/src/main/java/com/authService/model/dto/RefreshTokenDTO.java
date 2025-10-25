package com.authService.model.dto;

// dto for the refreshToken request when access token is invalid or expired

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenDTO {
    @NotBlank(message = "Refresh Token is required")
    private String token; // refresh token
}
