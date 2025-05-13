package com.traveller.kivi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        String getenv = System.getenv("SPRING_MAIL_USERNAME");
        if (getenv == null) {
            throw new RuntimeException("Environment variable SPRING_MAIL_USERNAME is not set");
        }
        message.setFrom(getenv);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}