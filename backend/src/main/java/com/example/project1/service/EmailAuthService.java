package com.example.project1.service;

public interface EmailAuthService {
    void sendVerificationLink(String email);
    boolean verifyToken(String token);
}
