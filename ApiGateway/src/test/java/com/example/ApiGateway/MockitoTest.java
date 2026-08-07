package com.example.ApiGateway;

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
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    private Route testRoute;

    @BeforeEach
    void setUp() {
        testRoute = new Route();
        testRoute.setId(1L);
        testRoute.setPath("/api/orders");
        testRoute.setService("order-service");
        testRoute.setUrl("http://order-service:8084");
        testRoute.setStatus("ACTIVE");
    }

    @Test
    void testFindById() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(testRoute));

        Route found = routeService.findById(1L);

        assertNotNull(found);
        assertEquals("/api/orders", found.getPath());
        assertEquals("order-service", found.getService());

        verify(routeRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Route found = routeService.findById(999L);

        assertNull(found);
        verify(routeRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveRoute() {
        when(routeRepository.save(any(Route.class))).thenReturn(testRoute);

        Route saved = routeService.save(testRoute);

        assertNotNull(saved);
        assertEquals("/api/orders", saved.getPath());
        verify(routeRepository, times(1)).save(any(Route.class));
    }

    @Test
    void testDeleteRoute() {
        doNothing().when(routeRepository).deleteById(anyLong());

        routeService.delete(1L);

        verify(routeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByPath() {
        when(routeRepository.findByPath(anyString())).thenReturn(testRoute);

        Route found = routeService.findByPath("/api/orders");

        assertNotNull(found);
        assertEquals("/api/orders", found.getPath());
        verify(routeRepository, times(1)).findByPath("/api/orders");
    }

    @Test
    void testFindByService() {
        java.util.List<Route> routes = java.util.Arrays.asList(testRoute);
        when(routeRepository.findByService(anyString())).thenReturn(routes);

        java.util.List<Route> found = routeService.findByService("order-service");

        assertNotNull(found);
        assertEquals(1, found.size());
        verify(routeRepository, times(1)).findByService("order-service");
    }

    @Test
    void testNeverCalled() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(testRoute));

        routeService.findById(1L);

        verify(routeRepository, never()).save(any(Route.class));
    }

    @Test
    void testAtLeastOnce() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(testRoute));

        routeService.findById(1L);
        routeService.findById(2L);

        verify(routeRepository, atLeastOnce()).findById(anyLong());
    }

    static class RouteRepository {
        Optional<Route> findById(Long id) {
            return Optional.empty();
        }

        Route save(Route route) {
            return route;
        }

        void deleteById(Long id) {
        }

        Route findByPath(String path) {
            return null;
        }

        java.util.List<Route> findByService(String service) {
            return java.util.Collections.emptyList();
        }
    }

    static class RouteService {
        private final RouteRepository routeRepository;

        public RouteService(RouteRepository routeRepository) {
            this.routeRepository = routeRepository;
        }

        Route findById(Long id) {
            return routeRepository.findById(id).orElse(null);
        }

        Route save(Route route) {
            return routeRepository.save(route);
        }

        void delete(Long id) {
            routeRepository.deleteById(id);
        }

        Route findByPath(String path) {
            return routeRepository.findByPath(path);
        }

        java.util.List<Route> findByService(String service) {
            return routeRepository.findByService(service);
        }
    }

    static class Route {
        private Long id;
        private String path;
        private String service;
        private String url;
        private String status;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
