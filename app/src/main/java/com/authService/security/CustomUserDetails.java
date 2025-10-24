package com.authService.security;

import com.authService.model.entity.UserInfo;
import com.authService.model.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    /*
        ----- could have done composition as well instead of separate user data members ------

        private UserInfo currentUser;

        public CustomUserDetails(UserInfo byUsername) {
            this.currentUser = byUsername;
        }

        @Override
        public String getPassword() {
            return currentUser.getPassword();
        }

    */

    public CustomUserDetails(UserInfo byUsername) {
        this.password = byUsername.getPassword();
        this.username = byUsername.getUsername();

        List<GrantedAuthority> auths = new ArrayList<>();

        for(UserRole role : byUsername.getRoles()) {
            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }

        this.authorities = auths;
    }

    // getter methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
