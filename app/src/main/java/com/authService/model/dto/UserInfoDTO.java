package com.authService.model.dto;

import com.authService.model.entity.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// contains the structure of user object for the request and response - /signup

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // snake
public class UserInfoDTO extends UserInfo {
    @NotBlank(message = "First name is required")
    @Size(min = 2, message = "First name must be of at least 2 characters")
    private String firstName;

    private String lastName; // lastName can be empty

    @Email(message = "Email is required")
    private String email;

    // can have phone number but is not used
}
