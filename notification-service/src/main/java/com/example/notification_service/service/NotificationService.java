package com.example.notification_service.service;

import com.example.notification_service.dto.NotificationRequest;
import com.example.notification_service.dto.NotificationResponse;
import com.example.notification_service.entity.Notification;
import com.example.notification_service.exception.NotificationNotFoundException;
import com.example.notification_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationResponse createNotification(NotificationRequest request) {
        log.info("Creating notification for recipient: {}", request.getRecipient());
        
        Notification notification = mapToEntity(request);
        Notification savedNotification = notificationRepository.save(notification);
        
        // Simulate sending notification (will be replaced by RabbitMQ consumer in Phase 6)
        simulateSendNotification(savedNotification);
        
        log.info("Notification created successfully with ID: {}", savedNotification.getId());
        return mapToResponse(savedNotification);
    }

    public NotificationResponse getNotificationById(Long id) {
        log.info("Fetching notification by ID: {}", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with ID: " + id));
        return mapToResponse(notification);
    }

    public List<NotificationResponse> getNotificationsByRecipient(String recipient) {
        log.info("Fetching notifications for recipient: {}", recipient);
        List<Notification> notifications = notificationRepository.findByRecipient(recipient);
        return notifications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getNotificationsByOrderNumber(String orderNumber) {
        log.info("Fetching notifications for order number: {}", orderNumber);
        List<Notification> notifications = notificationRepository.findByOrderNumber(orderNumber);
        return notifications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getAllNotifications() {
        log.info("Fetching all notifications");
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getNotificationsByStatus(String status) {
        log.info("Fetching notifications by status: {}", status);
        List<Notification> notifications = notificationRepository.findByStatus(status);
        return notifications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteNotification(Long id) {
        log.info("Deleting notification with ID: {}", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with ID: " + id));
        notificationRepository.delete(notification);
        log.info("Notification deleted successfully with ID: {}", id);
    }

    private void simulateSendNotification(Notification notification) {
        log.info("Simulating sending notification to: {}", notification.getRecipient());
        // In Phase 6, this will be replaced by RabbitMQ consumer
        notification.setStatus("SENT");
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    private Notification mapToEntity(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setType(request.getType());
        notification.setRecipient(request.getRecipient());
        notification.setSubject(request.getSubject());
        notification.setMessage(request.getMessage());
        notification.setStatus("PENDING");
        notification.setChannel(request.getChannel());
        notification.setMetadata(request.getMetadata());
        notification.setOrderNumber(request.getOrderNumber());
        return notification;
    }

    private NotificationResponse mapToResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setType(notification.getType());
        response.setRecipient(notification.getRecipient());
        response.setSubject(notification.getSubject());
        response.setMessage(notification.getMessage());
        response.setStatus(notification.getStatus());
        response.setChannel(notification.getChannel());
        response.setMetadata(notification.getMetadata());
        response.setOrderNumber(notification.getOrderNumber());
        response.setCreatedAt(notification.getCreatedAt());
        response.setSentAt(notification.getSentAt());
        return response;
    }
}
