package com.enigma.library_management_system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Libary Management System",
                version = "1.0",
                contact = @Contact(
                        name = "I Ketut Adi Suprianta",
                        email = "adisuprianta13@gmail.com"
                )
        )
)

public class OpenApiConfiguration {
}
