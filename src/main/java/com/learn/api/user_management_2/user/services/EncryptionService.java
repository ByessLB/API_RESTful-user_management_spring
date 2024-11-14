package com.learn.api.user_management_2.user.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptionService {

    private final PasswordEncoder passwordEncoder;

    public EncryptionService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean isPasswordValid(String providedPassword, String securedPassword) {
        return passwordEncoder.matches(providedPassword, securedPassword);
    }
}
