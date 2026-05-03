package com.parv.service;

import com.parv.dto.RegisterRequest;
import com.parv.dto.AuthenticationRequest;
import com.parv.dto.AuthenticationResponse;
import com.parv.entity.Role;
import com.parv.entity.User;
import com.parv.repository.UserRepository;
import com.parv.security.JwtService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${application.security.admin.username}")
    private String adminUsername;

    @Value("${application.security.admin.password}")
    private String adminPassword;

    @Value("${application.security.admin.email}")
    private String adminEmail;

    public AuthenticationResponse register(RegisterRequest request) {
        if (adminEmail.equalsIgnoreCase(request.getEmail()) ||
            userRepository.findByUsername(request.getEmail()).isPresent() ||
            userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        if (request.getPassword() == null || !request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .username(request.getFirstName().toLowerCase() + "_" + request.getLastName().toLowerCase()) // firstName_lastName as username
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole().name())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String username = request.getUsername().trim();
        String password = request.getPassword();

        // Check if it's the hardcoded admin login (allow both username and email)
        if ((adminUsername.equalsIgnoreCase(username) || adminEmail.equalsIgnoreCase(username)) 
            && adminPassword.equals(password)) {
            
            // Create a temporary User object for the admin (not saved in DB)
            User adminUser = User.builder()
                    .username(adminUsername)
                    .email(adminEmail)
                    .role(Role.ADMIN)
                    .build();
            
            var jwtToken = jwtService.generateToken(adminUser);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .username(adminUsername)
                    .role(Role.ADMIN.name())
                    .firstName("Admin")
                    .lastName("User")
                    .build();
        }

        var user = userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new RuntimeException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        password
                )
        );
        
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole().name())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
