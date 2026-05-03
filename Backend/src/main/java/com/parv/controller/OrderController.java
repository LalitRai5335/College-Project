package com.parv.controller;

import com.parv.entity.Order;
import com.parv.entity.User;
import com.parv.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order APIs", description = "Endpoints for placing and managing orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Place Order", description = "Allows logged in users to place an order")
    @PostMapping
    public ResponseEntity<Order> placeOrder(
            @RequestBody Order order,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(orderService.placeOrder(order, user));
    }

    @Operation(summary = "My Orders", description = "Fetches orders for the currently logged in user")
    @GetMapping("/my")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getUserOrders(user));
    }

    @Operation(summary = "All Orders (Admin)", description = "Fetches all orders (Admin only)")
    @GetMapping("/admin/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @Operation(summary = "Update Order Status (Admin)", description = "Updates order status (Admin only)")
    @PatchMapping("/admin/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
}
