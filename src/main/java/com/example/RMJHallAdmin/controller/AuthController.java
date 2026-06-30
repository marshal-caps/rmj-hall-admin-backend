package com.example.RMJHallAdmin.controller;

import com.example.RMJHallAdmin.dto.LoginRequest;
import com.example.RMJHallAdmin.dto.LoginResponse;
import com.example.RMJHallAdmin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://project-c3wrs.vercel.app"
})
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        boolean success = authService.login(
                request.getUsername(),
                request.getPassword()
        );

        if (success) {
            return ResponseEntity.ok(
                    new LoginResponse(true, "Login Successful")
            );
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "Invalid Username or Password"));
    }

}