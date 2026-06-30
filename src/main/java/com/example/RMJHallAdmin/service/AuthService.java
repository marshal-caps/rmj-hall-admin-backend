package com.example.RMJHallAdmin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    public boolean login(String username, String password) {

        return adminUsername.equals(username)
                && adminPassword.equals(password);

    }

}