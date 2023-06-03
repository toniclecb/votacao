package com.toniclecb.pauta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI usersMicroserviceOpenAPI() {
		return new OpenAPI().info(new Info().title("Pauta Votação")
				.description("Fornece uma API para criação de pautas e votação").version("1.0"));
	}
}
