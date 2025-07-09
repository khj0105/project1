package com.example.project1.teamproject.service;

public interface EmailSender {
    void send(String to, String subject, String text);
}
