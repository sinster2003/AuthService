package com.authService.auth;

/* Main goal of this config bean is to provide password encoding logic */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // bean + configuration logic
public class UserConfig {

    @Bean // this creates PasswordEncoder to be a bean anc can be injected inside UserDetailsServiceImpl
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}