package com.example.project1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 메일 발송을 위한 SMTP 설정 클래스
 */
@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host; // 메일 서버 호스트

    @Value("${spring.mail.port}")
    private int port; // 메일 서버 포트

    @Value("${spring.mail.username}")
    private String username; // 로그인용 사용자명

    @Value("${spring.mail.password}")
    private String password; // 로그인용 비밀번호

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true"); // 보안 강화 옵션 유지
        props.put("mail.smtp.ssl.trust", host);           // SSL 인증 예외 방지
        props.put("mail.debug", "true");

        return mailSender;
    }
}
