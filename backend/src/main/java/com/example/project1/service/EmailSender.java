package com.example.project1.service;

public interface EmailSender {
    void send(String to, String subject, String text);
}
