package com.cyberforgetech.tasktracker.dto.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        long expiresInSeconds
) {}