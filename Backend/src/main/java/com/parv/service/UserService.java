package com.parv.service;

import com.parv.entity.User;
import com.parv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public long countTotalUsers() {
        return userRepository.count();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateProfile(String username, String firstName, String lastName, String phoneNumber, String profileImageUrl) {
        User user = getUserByUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        if (profileImageUrl != null) {
            user.setProfileImageUrl(profileImageUrl);
        }
        return userRepository.save(user);
    }
}
