package com.taskmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 3 configuration.
 * Access Swagger UI at: http://localhost:8080/swagger-ui.html
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "AI-Powered Task Manager API",
        version = "1.0",
        description = "REST API for the AI-Powered Task Management Portal"
    )
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Enter JWT token obtained from /api/auth/login"
)
public class OpenApiConfig {
    // Configuration is done via annotations above
}
