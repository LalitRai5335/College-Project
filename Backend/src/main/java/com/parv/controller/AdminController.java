package com.parv.controller;

import com.parv.entity.*;
import com.parv.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin APIs", description = "Protected endpoints for website management")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final ProductService productService;
    private final ContactService contactService;
    private final ReviewService reviewService;
    private final FileStorageService fileStorageService;
    private final OrderService orderService;
    private final UserService userService;
    private final TeaBenefitService teaBenefitService;

    // Benefit Management
    @Operation(summary = "List All Benefits", description = "Fetches all tea benefits")
    @GetMapping("/benefits")
    public ResponseEntity<List<TeaBenefit>> getAllBenefitsAdmin() {
        return ResponseEntity.ok(teaBenefitService.getAllBenefits());
    }

    @Operation(summary = "Add New Benefit", description = "Creates a new tea benefit")
    @PostMapping("/benefits")
    public ResponseEntity<TeaBenefit> addBenefit(@RequestBody TeaBenefit benefit) {
        return ResponseEntity.ok(teaBenefitService.createBenefit(benefit));
    }

    @Operation(summary = "Update Benefit", description = "Updates an existing tea benefit")
    @PutMapping("/benefits/{id}")
    public ResponseEntity<TeaBenefit> updateBenefit(@PathVariable Long id, @RequestBody TeaBenefit benefit) {
        return ResponseEntity.ok(teaBenefitService.updateBenefit(id, benefit));
    }

    @Operation(summary = "Delete Benefit", description = "Removes a tea benefit")
    @DeleteMapping("/benefits/{id}")
    public ResponseEntity<Void> deleteBenefit(@PathVariable Long id) {
        teaBenefitService.deleteBenefit(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Admin Dashboard Stats", description = "Provides total products, new messages, reviews, and orders count")
    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", productService.countTotalProducts());
        stats.put("newMessages", contactService.countNewMessages());
        stats.put("totalReviews", reviewService.getAllReviews().size());
        stats.put("totalOrders", orderService.getAllOrders().size());
        stats.put("totalUsers", userService.countTotalUsers());
        return ResponseEntity.ok(stats);
    }

    // User Management
    @Operation(summary = "List All Users", description = "Fetches all registered users")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Delete User", description = "Removes a user from the system")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Product Management
    @Operation(summary = "List All Products", description = "Fetches all products (active and inactive)")
    @GetMapping("/products")
    public ResponseEntity<List<TeaProduct>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Add New Product", description = "Creates a new product with optional image upload")
    @PostMapping("/products")
    public ResponseEntity<TeaProduct> addProduct(
            @RequestParam("title") String title,
            @RequestParam(value = "subtitle", required = false) String subtitle,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "originalPrice", required = false) Double originalPrice,
            @RequestParam(value = "stockQuantity", required = false) Integer stockQuantity,
            @RequestParam(value = "weight", required = false) String weight,
            @RequestParam(value = "sku", required = false) String sku,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = fileStorageService.storeFile(image);
        }

        TeaProduct product = TeaProduct.builder()
                .title(title)
                .subtitle(subtitle)
                .description(description)
                .category(category)
                .price(price)
                .originalPrice(originalPrice)
                .stockQuantity(stockQuantity != null ? stockQuantity : 0)
                .weight(weight)
                .sku(sku)
                .imageUrl(imageUrl)
                .isActive(true)
                .build();

        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @Operation(summary = "Update Product", description = "Edits an existing product details or image (Supports both PUT and POST)")
    @RequestMapping(value = "/products/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<TeaProduct> updateProduct(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam(value = "subtitle", required = false) String subtitle,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "price", required = false) Double price,
            @RequestParam(value = "originalPrice", required = false) Double originalPrice,
            @RequestParam(value = "stockQuantity", required = false) Integer stockQuantity,
            @RequestParam(value = "weight", required = false) String weight,
            @RequestParam(value = "sku", required = false) String sku,
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        TeaProduct product = productService.getProductById(id);
        product.setTitle(title);
        product.setSubtitle(subtitle);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setOriginalPrice(originalPrice);
        if (stockQuantity != null) product.setStockQuantity(stockQuantity);
        product.setWeight(weight);
        product.setSku(sku);
        if (isActive != null) product.setActive(isActive);

        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(image);
            product.setImageUrl(imageUrl);
        }

        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @Operation(summary = "Delete Product", description = "Permanently removes a product from the database")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Message Management
    @Operation(summary = "View Contact Messages", description = "Fetches all messages submitted via contact form")
    @GetMapping("/messages")
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        return ResponseEntity.ok(contactService.getAllMessages());
    }

    // Review Management
    @Operation(summary = "List All Reviews", description = "Fetches all reviews for moderation")
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @Operation(summary = "Approve Review", description = "Marks a customer review as approved for public view")
    @PatchMapping("/reviews/{id}")
    public ResponseEntity<Review> approveReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.approveReview(id));
    }

    @Operation(summary = "Delete Review", description = "Deletes a review")
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
