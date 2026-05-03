package com.parv.controller;

import com.parv.entity.ContactMessage;
import com.parv.entity.Review;
import com.parv.entity.TeaBenefit;
import com.parv.entity.TeaProduct;
import com.parv.service.ContactService;
import com.parv.service.ProductService;
import com.parv.service.ReviewService;
import com.parv.service.TeaBenefitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Public APIs", description = "Endpoints accessible by all users")
public class PublicController {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final ContactService contactService;
    private final TeaBenefitService teaBenefitService;

    @Operation(summary = "Get All Benefits", description = "Fetches all tea benefits")
    @GetMapping("/benefits")
    public ResponseEntity<List<TeaBenefit>> getAllBenefits() {
        return ResponseEntity.ok(teaBenefitService.getAllBenefits());
    }

    @Operation(summary = "Get Active Products", description = "Fetches all products marked as active")
    @GetMapping("/products")
    public ResponseEntity<List<TeaProduct>> getActiveProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @Operation(summary = "Get Approved Reviews", description = "Fetches all reviews approved by admin")
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getApprovedReviews() {
        return ResponseEntity.ok(reviewService.getAllApprovedReviews());
    }

    @Operation(summary = "Submit Contact Form", description = "Submits a contact message and triggers email notification")
    @PostMapping("/contact")
    public ResponseEntity<ContactMessage> submitContact(@RequestBody ContactMessage message) {
        return ResponseEntity.ok(contactService.saveMessage(message));
    }

    @Operation(summary = "Submit a Review", description = "Allows customers to submit a review (requires admin approval)")
    @PostMapping("/reviews")
    public ResponseEntity<Review> submitReview(@RequestBody Review review) {
        return ResponseEntity.ok(reviewService.saveReview(review));
    }
}
