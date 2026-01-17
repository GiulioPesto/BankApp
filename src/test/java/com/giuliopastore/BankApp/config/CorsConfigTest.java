package com.giuliopastore.BankApp.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorsConfigTest {

    @Mock
    private CorsRegistry corsRegistry;

    @Mock
    private CorsRegistration corsRegistration;

    @Test
    void testAddCorsMappings() {
        // Given
        CorsConfig corsConfig = new CorsConfig();
        when(corsRegistry.addMapping(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedOrigins(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods(anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders(anyString())).thenReturn(corsRegistration);
        when(corsRegistration.allowCredentials(anyBoolean())).thenReturn(corsRegistration);

        // When
        corsConfig.addCorsMappings(corsRegistry);

        // Then
        verify(corsRegistry, times(1)).addMapping("/**");
        verify(corsRegistration, times(1)).allowedOrigins("http://localhost:3000", "http://localhost:8080", "http://localhost:5173", "https://pestobank.wasd.solutions", "https://www.pestobank.wasd.solutions");
        verify(corsRegistration, times(1)).allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
        verify(corsRegistration, times(1)).allowedHeaders("*");
        verify(corsRegistration, times(1)).allowCredentials(true);
    }

    @Test
    void testCorsConfigCreation() {
        // Given & When
        CorsConfig corsConfig = new CorsConfig();

        // Then - Should be created successfully as a WebMvcConfigurer
        assertNotNull(corsConfig);
    }
}

