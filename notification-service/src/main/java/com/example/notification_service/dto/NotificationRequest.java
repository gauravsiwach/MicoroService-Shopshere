package com.example.notification_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @NotBlank(message = "Type is required")
    @Size(max = 100, message = "Type must not exceed 100 characters")
    private String type;

    @NotBlank(message = "Recipient is required")
    @Email(message = "Recipient must be a valid email")
    @Size(max = 100, message = "Recipient must not exceed 100 characters")
    private String recipient;

    @NotBlank(message = "Subject is required")
    @Size(max = 500, message = "Subject must not exceed 500 characters")
    private String subject;

    @NotBlank(message = "Message is required")
    private String message;

    @Size(max = 50, message = "Channel must not exceed 50 characters")
    private String channel = "EMAIL";

    private String metadata;

    @Size(max = 50, message = "Order number must not exceed 50 characters")
    private String orderNumber;
}
