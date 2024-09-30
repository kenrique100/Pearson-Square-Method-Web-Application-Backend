package com.feedformulation.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            // Overrides the default CORS configuration to add custom mappings
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {

                // Configures CORS settings for endpoints related to custom feed formulations
                registry.addMapping("/api/feed-formulations/**")
                        .allowedOrigins("http://localhost:3000") // Only allow requests from the specified frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Restrict HTTP methods to the specified ones
                        .allowedHeaders("*") // Allow all headers from the frontend
                        .allowCredentials(true); // Enable sending cookies and credentials in requests

                // Configures CORS settings for endpoints related to default feed formulations
                registry.addMapping("/api/feed-formulation/**")
                        .allowedOrigins("http://localhost:3000") // Only allow requests from the specified frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Restrict HTTP methods to the specified ones
                        .allowedHeaders("*") // Allow all headers from the frontend
                        .allowCredentials(true); // Enable sending cookies and credentials in requests

            }
        };
    }
}
