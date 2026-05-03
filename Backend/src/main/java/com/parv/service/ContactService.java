package com.parv.service;

import com.parv.entity.ContactMessage;
import com.parv.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactMessageRepository contactMessageRepository;
    private final EmailService emailService;

    public ContactMessage saveMessage(ContactMessage message) {
        ContactMessage savedMessage = contactMessageRepository.save(message);
        try {
            emailService.sendContactNotification(
                    message.getName(),
                    message.getEmail(),
                    message.getSubject(),
                    message.getMessage()
            );
        } catch (Exception e) {
            System.err.println("Failed to send email notification: " + e.getMessage());
        }
        return savedMessage;
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAll();
    }

    public long countNewMessages() {
        // Simple count for now
        return contactMessageRepository.count();
    }
}
