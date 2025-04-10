package br.com.fiap.beach_play_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Beach-Play", version = "v1", description = "Sistema de gestão e reserva de quadras de vôlei."))

public class BeachPlayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeachPlayApiApplication.class, args);
	}

}
