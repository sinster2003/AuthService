package com.authService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// dto for the login request

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO {
    private String username;
    private String password;
}
