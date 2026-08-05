package com.example.notification_service.controller;

import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Notification Management", description = "APIs for managing notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "Create a new notification")
    public ResponseEntity<NotificationResponse> createNotification(@Valid @RequestBody NotificationRequest request) {
        log.info("REST request to create notification");
        NotificationResponse notificationResponse = notificationService.createNotification(request);
        return new ResponseEntity<>(notificationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification by ID")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable Long id) {
        log.info("REST request to get notification by id: {}", id);
        NotificationResponse notificationResponse = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notificationResponse);
    }

    @GetMapping("/recipient/{recipient}")
    @Operation(summary = "Get notifications by recipient email")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByRecipient(@PathVariable String recipient) {
        log.info("REST request to get notifications by recipient: {}", recipient);
        List<NotificationResponse> notifications = notificationService.getNotificationsByRecipient(recipient);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/order/{orderNumber}")
    @Operation(summary = "Get notifications by order number")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByOrderNumber(@PathVariable String orderNumber) {
        log.info("REST request to get notifications by order number: {}", orderNumber);
        List<NotificationResponse> notifications = notificationService.getNotificationsByOrderNumber(orderNumber);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get notifications by status")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByStatus(@PathVariable String status) {
        log.info("REST request to get notifications by status: {}", status);
        List<NotificationResponse> notifications = notificationService.getNotificationsByStatus(status);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping
    @Operation(summary = "Get all notifications")
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        log.info("REST request to get all notifications");
        List<NotificationResponse> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.info("REST request to delete notification with id: {}", id);
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
