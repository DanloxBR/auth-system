package com.danieldev.authsystem.controller;

import com.danieldev.authsystem.dto.request.AuthRequest;
import com.danieldev.authsystem.dto.response.AuthResponse;
import com.danieldev.authsystem.security.jwt.JwtUtil;
import com.danieldev.authsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        authService.register(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("User created");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(request.getUsername());

        String token = jwtUtil.generateToken(String.valueOf(userDetails));
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

