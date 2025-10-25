package com.authService.auth;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * Main goal is to determine which route is protected & not protected
 */

@Configuration // bean + configuration logic
@EnableMethodSecurity // enables method level security annotations
@Data
public class SecurityConfig {

}
