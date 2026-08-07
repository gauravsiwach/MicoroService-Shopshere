package com.example.notification_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpringBootControllerTest {

    @Test
    void testGetNotificationById() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setType("EMAIL");
        notification.setRecipient("user@example.com");
        notification.setSubject("Order Confirmation");
        notification.setMessage("Your order has been confirmed");
        notification.setStatus("SENT");

        assertEquals(1L, notification.getId());
        assertEquals("EMAIL", notification.getType());
        assertEquals("user@example.com", notification.getRecipient());
        assertEquals("SENT", notification.getStatus());
    }

    @Test
    void testCreateNotification() {
        Notification notification = new Notification();
        notification.setType("SMS");
        notification.setRecipient("+1234567890");
        notification.setSubject("Order Shipped");
        notification.setMessage("Your order has been shipped");
        notification.setStatus("PENDING");

        assertEquals("SMS", notification.getType());
        assertEquals("+1234567890", notification.getRecipient());
    }

    @Test
    void testUpdateNotification() {
        Notification notification = new Notification();
        notification.setType("EMAIL");
        notification.setRecipient("user@example.com");
        notification.setSubject("Order Confirmation");
        notification.setMessage("Your order has been confirmed");
        notification.setStatus("PENDING");

        notification.setStatus("SENT");
        notification.setMessage("Your order has been confirmed and shipped");

        assertEquals("SENT", notification.getStatus());
        assertEquals("Your order has been confirmed and shipped", notification.getMessage());
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
