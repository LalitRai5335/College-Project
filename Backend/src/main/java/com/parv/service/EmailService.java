package com.parv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendContactNotification(String name, String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("Groupaspn@gmail.com");
        mailMessage.setSubject("New Contact Message from " + name + ": " + subject);
        mailMessage.setText("You have a new message from " + name + " (" + email + "):\n\n" + message);
        mailSender.send(mailMessage);
    }
}
