package com.authService.model.dto;

// dto for the refreshToken request when access token is invalid or expired

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokenDTO {
    private String token; // refresh token
}
