package com.example.notification_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockitoTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private Notification testNotification;

    @BeforeEach
    void setUp() {
        testNotification = new Notification();
        testNotification.setId(1L);
        testNotification.setType("EMAIL");
        testNotification.setRecipient("user@example.com");
        testNotification.setSubject("Order Confirmation");
        testNotification.setMessage("Your order has been confirmed");
        testNotification.setStatus("SENT");
    }

    @Test
    void testFindById() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(testNotification));

        Notification found = notificationService.findById(1L);

        assertNotNull(found);
        assertEquals("EMAIL", found.getType());
        assertEquals("user@example.com", found.getRecipient());

        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.empty());

        Notification found = notificationService.findById(999L);

        assertNull(found);
        verify(notificationRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveNotification() {
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        Notification saved = notificationService.save(testNotification);

        assertNotNull(saved);
        assertEquals("EMAIL", saved.getType());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testDeleteNotification() {
        doNothing().when(notificationRepository).deleteById(anyLong());

        notificationService.delete(1L);

        verify(notificationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByRecipient() {
        java.util.List<Notification> notifications = java.util.Arrays.asList(testNotification);
        when(notificationRepository.findByRecipient(anyString())).thenReturn(notifications);

        java.util.List<Notification> found = notificationService.findByRecipient("user@example.com");

        assertNotNull(found);
        assertEquals(1, found.size());
        verify(notificationRepository, times(1)).findByRecipient("user@example.com");
    }

    @Test
    void testFindByStatus() {
        java.util.List<Notification> notifications = java.util.Arrays.asList(testNotification);
        when(notificationRepository.findByStatus(anyString())).thenReturn(notifications);

        java.util.List<Notification> found = notificationService.findByStatus("SENT");

        assertNotNull(found);
        assertEquals(1, found.size());
        verify(notificationRepository, times(1)).findByStatus("SENT");
    }

    @Test
    void testNeverCalled() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(testNotification));

        notificationService.findById(1L);

        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void testAtLeastOnce() {
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(testNotification));

        notificationService.findById(1L);
        notificationService.findById(2L);

        verify(notificationRepository, atLeastOnce()).findById(anyLong());
    }

    static class NotificationRepository {
        Optional<Notification> findById(Long id) {
            return Optional.empty();
        }

        Notification save(Notification notification) {
            return notification;
        }

        void deleteById(Long id) {
        }

        java.util.List<Notification> findByRecipient(String recipient) {
            return java.util.Collections.emptyList();
        }

        java.util.List<Notification> findByStatus(String status) {
            return java.util.Collections.emptyList();
        }
    }

    static class NotificationService {
        private final NotificationRepository notificationRepository;

        public NotificationService(NotificationRepository notificationRepository) {
            this.notificationRepository = notificationRepository;
        }

        Notification findById(Long id) {
            return notificationRepository.findById(id).orElse(null);
        }

        Notification save(Notification notification) {
            return notificationRepository.save(notification);
        }

        void delete(Long id) {
            notificationRepository.deleteById(id);
        }

        java.util.List<Notification> findByRecipient(String recipient) {
            return notificationRepository.findByRecipient(recipient);
        }

        java.util.List<Notification> findByStatus(String status) {
            return notificationRepository.findByStatus(status);
        }
    }

    static class Notification {
        private Long id;
        private String type;
        private String recipient;
        private String subject;
        private String message;
        private String status;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRecipient() {
            return recipient;
        }

        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
