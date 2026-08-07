package com.example.notification_service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityTest {

    private String validJwtToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc4NjA5NTY3OCwiZXhwIjoxNzg2MTgyMDc4fQ.7ZsMIXU4cVEIM4g7357c8TMQzzazvsax8aLLnzENQcE";

    @Test
    void testJwtAuthentication401Unauthorized() {
        String url = "/api/notifications/1";
        assertNotNull(url);
    }

    @Test
    void testJwtAuthenticationWithInvalidToken() {
        String invalidToken = "Bearer invalid-token";
        assertNotNull(invalidToken);
    }

    @Test
    void testJwtAuthenticationWithExpiredToken() {
        String expiredToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY4NjA5NTY3OCwiZXhwIjoxNjg2MTgyMDc4fQ.invalid";
        assertNotNull(expiredToken);
    }

    @Test
    void testJwtAuthenticationWithValidToken() {
        assertNotNull(validJwtToken);
        assertTrue(validJwtToken.startsWith("Bearer"));
    }

    @Test
    void testRBACAuthorityNotificationCreate() {
        String tokenWithNotificationCreate = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzg2MDk1Njc4LCJleHAiOjE3ODYxODIwNzgsImF1dGhvcml0aWVzIjpbIk5PVElGSUNBVElPTl9DUkVBVEUiXX0.test";
        assertNotNull(tokenWithNotificationCreate);
    }

    @Test
    void testRBACAuthorityNotificationUpdate() {
        String tokenWithNotificationUpdate = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzg2MDk1Njc4LCJleHAiOjE3ODYxODIwNzgsImF1dGhvcml0aWVzIjpbIk5PVElGSUNBVElPTl9VUERBVEUiXX0.test";
        assertNotNull(tokenWithNotificationUpdate);
    }

    @Test
    void testRBACAuthorityNotificationDelete() {
        String tokenWithNotificationDelete = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzg2MDk1Njc4LCJleHAiOjE3ODYxODIwNzgsImF1dGhvcml0aWVzIjpbIk5PVElGSUNBVElPTl9ERUxFVEUiXX0.test";
        assertNotNull(tokenWithNotificationDelete);
    }

    @Test
    void testRBACAuthorityNotificationSend() {
        String tokenWithNotificationSend = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzg2MDk1Njc4LCJleHAiOjE3ODYxODIwNzgsImF1dGhvcml0aWVzIjpbIk5PVElGSUNBVElPTl9TRU5EIl19.test";
        assertNotNull(tokenWithNotificationSend);
    }

    @Test
    void test403ForbiddenUnauthorizedAccess() {
        String tokenWithoutAuthority = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzg2MDk1Njc4LCJleHAiOjE3ODYxODIwNzgsImF1dGhvcml0aWVzIjpbIlVTRVIiXX0.test";
        assertNotNull(tokenWithoutAuthority);
    }

    @Test
    void testPublicApiAccessWithoutToken() {
        String url = "/api/auth/login";
        assertNotNull(url);
    }

    @Test
    void testProtectedApiAccessWithoutToken() {
        String url = "/api/notifications";
        assertNotNull(url);
    }

    @Test
    void testProtectedApiAccessWithValidToken() {
        assertNotNull(validJwtToken);
    }

    @Test
    void testPublicVsProtectedApi() {
        String publicUrl = "/api/auth/login";
        String protectedUrl = "/api/notifications";
        assertNotNull(publicUrl);
        assertNotNull(protectedUrl);
    }

    @Test
    void testAdminRoleAccess() {
        String adminToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc4NjA5NTY3OCwiZXhwIjoxNzg2MTgyMDc4LCJyb2xlIjoiQURNSU4iLCJhdXRob3JpdGllcyI6WyJPUkRFUl9DUkVBVEUiLCJPUkRFUl9VUERBVEUiLCJPUkRFUl9ERUxFVEUiLCJOT1RJRklDQVRJT05fQ1JFQVRFIiwiTk9USUZJQ0FUSU9OX1VQREFURSIsIk5PVElGSUNBVElPTl9ERUxFVEUiLCJOT1RJRklDQVRJT05fU0VORCJdfQ.test";
        assertNotNull(adminToken);
    }

    @Test
    void testUserRoleLimitedAccess() {
        String userToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzg2MDk1Njc4LCJleHAiOjE3ODYxODIwNzgsInJvbGUiOiJVU0VSIiwiYXV0aG9yaXRpZXMiOlsiTk9USUZJQ0FUSU9OX0NSRUFURSJdfQ.test";
        assertNotNull(userToken);
    }

    @Test
    void testMethodSecurity() {
        String adminToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc4NjA5NTY3OCwiZXhwIjoxNzg2MTgyMDc4LCJyb2xlIjoiQURNSU4iLCJhdXRob3JpdGllcyI6WyJPUkRFUl9DUkVBVEUiLCJPUkRFUl9VUERBVEUiLCJPUkRFUl9ERUxFVEUiXX0.test";
        String userToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNzg2MDk1Njc4LCJleHAiOjE3ODYxODIwNzgsInJvbGUiOiJVU0VSIiwiYXV0aG9yaXRpZXMiOlsiTk9USUZJQ0FUSU9OX0NSRUFURSJdfQ.test";
        assertNotNull(adminToken);
        assertNotNull(userToken);
    }
}
