package com.feedformulation.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Value("${frontend.url}")  // Fixed typo by adding the closing curly brace
    private String frontendUrl;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                // Configure CORS for /api/feed-formulations endpoints
                registry.addMapping("/api/feed-formulations/**")
                        .allowedOrigins(frontendUrl) // Frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                        .allowedHeaders("*") // Allowed headers
                        .allowCredentials(true); // Allow credentials (e.g., cookies)

                // Configure CORS for /v1/api/feed-formulation endpoints
                registry.addMapping("/api/feed-formulation/**")
                        .allowedOrigins(frontendUrl) // Frontend origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                        .allowedHeaders("*") // Allowed headers
                        .allowCredentials(true); // Allow credentials (e.g., cookies)
            }
        };
    }
}
