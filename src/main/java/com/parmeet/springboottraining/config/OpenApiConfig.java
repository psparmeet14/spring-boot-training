package com.parmeet.springboottraining.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Parmeet Singh",
                        email = "psparmeet@gmail.com",
                        url = "https://github.com/psparmeet14"
                ),
                title = "OpenApi specification - Spring Boot Training",
                description = "OpenApi documentation for Spring Boot Training",
                version = "1.0",
                license = @License(
                        name = "Test License name",
                        url = "https://some-license-url-test.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8081"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Bearer authentication description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
