package com.chatop.chatop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Swagger/OpenAPI pour la documentation de l'API.
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API ChâTop")
                        .version("1.0")
                        .description("Documentation de l'API ChâTop avec JWT")
                );
    }
}
