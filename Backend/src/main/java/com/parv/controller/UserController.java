package com.parv.controller;

import com.parv.entity.User;
import com.parv.service.UserService;
import com.parv.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "Endpoints for managing user profile")
public class UserController {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Operation(summary = "Get Profile", description = "Fetches the current user's profile")
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUserByUsername(userDetails.getUsername()));
    }

    @Operation(summary = "Update Profile", description = "Updates the current user's profile details and image")
    @PostMapping("/profile")
    public ResponseEntity<User> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        String profileImageUrl = null;
        if (image != null && !image.isEmpty()) {
            profileImageUrl = fileStorageService.storeFile(image);
        }
        
        User updatedUser = userService.updateProfile(
                userDetails.getUsername(),
                firstName,
                lastName,
                phoneNumber,
                profileImageUrl
        );
        return ResponseEntity.ok(updatedUser);
    }
}
