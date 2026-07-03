package com.project.ems.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeManagementAPI() {

        return new OpenAPI().info(new Info().title("Employee Management System API").version("1.0")
                        .description("REST API for Employee Management System")
                        .contact(new Contact().name("Syed Azhaan Rizvi").email("azzu2410@gmail.com")))
                .externalDocs(new ExternalDocumentation().description("GitHub Repository")
                        .url("https://github.com/Azhaan24"));
    }
}