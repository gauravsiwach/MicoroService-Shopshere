package com.example.notification_service.repository;

import com.example.notification_service.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipient(String recipient);
    List<Notification> findByOrderNumber(String orderNumber);
    List<Notification> findByStatus(String status);
    Optional<Notification> findByOrderNumberAndType(String orderNumber, String type);
}
