package com.cyberforgetech.tasktracker.service;

import com.cyberforgetech.tasktracker.dto.auth.AuthResponse;
import com.cyberforgetech.tasktracker.dto.auth.LoginRequest;
import com.cyberforgetech.tasktracker.dto.auth.RefreshRequest;
import com.cyberforgetech.tasktracker.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthService(JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public AuthResponse login(LoginRequest request) {
        // This will throw if credentials are wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());

        String accessToken = jwtService.generateAccessToken(userDetails.getUsername());
        String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

        return new AuthResponse(accessToken, refreshToken, 900); // 900 seconds = 15 min
    }

    public AuthResponse refresh(RefreshRequest request) {
        String username = jwtService.extractUsername(request.refreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenValid(request.refreshToken(), userDetails)) {
            String newAccessToken = jwtService.generateAccessToken(username);
            return new AuthResponse(newAccessToken, request.refreshToken(), 900);
        }

        throw new RuntimeException("Invalid or expired refresh token");
    }
}