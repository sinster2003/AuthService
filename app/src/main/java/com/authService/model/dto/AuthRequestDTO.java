package com.authService.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Username is required")
    @Size(min = 2, message = "Username must contain at least of 2 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must contain at least of 6 characters")
    private String password;
}
