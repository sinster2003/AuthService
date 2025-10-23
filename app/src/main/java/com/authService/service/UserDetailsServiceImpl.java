package com.authService.service;

import com.authService.entities.UserInfo;
import com.authService.repository.UserRepository;
import com.authService.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
* Main goal of the UserDetailsService:
* Is to load the user using the username interacting with the database via repository layer
* Returns CustomUserDetails which formats the UserInfo in the form of username, password & roles
* */

@Service // bean + business logic
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // reads the user from the database and returns in the standardized spring security UserDetails version
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userLoadedFromDB = userRepository.findByUsername(username);

        if (userLoadedFromDB.isEmpty()) {
            throw new UsernameNotFoundException("Username not found. Please sign up");
        }

        return new CustomUserDetails(userLoadedFromDB.get());
    }
}
