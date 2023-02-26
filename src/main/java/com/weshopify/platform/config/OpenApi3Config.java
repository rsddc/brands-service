package com.weshopify.platform.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "brands-api", scheme = "bearer")
@OpenAPIDefinition(info = @Info(title = "Brand API", version = "2.0", description = "Brand Resource"))
public class OpenApi3Config {
}
