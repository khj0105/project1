package com.example.project1.teamproject.service;

public interface EmailAuthService {
    void sendVerificationLink(String email);
    boolean verifyToken(String token);
}
