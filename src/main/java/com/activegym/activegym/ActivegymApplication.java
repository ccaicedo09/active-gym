package com.activegym.activegym;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main class for the ActiveGym Management System application.
 * <p>
 * This class serves as the entry point to the Spring Boot application. It initializes the Spring
 * context, starts the embedded web server, and defines essential beans like {@link ModelMapper}.
 * </p>
 */
@SpringBootApplication
public class ActivegymApplication {

	/**
	 * Launches the Spring Boot application.
	 * <p>
	 * This method uses {@link SpringApplication#run(Class, String...)} to start the application
	 * with the provided command-line arguments.
	 * </p>
	 *
	 * @param args Command-line arguments passed to the application (if any).
	 */
	public static void main(String[] args) {
		SpringApplication.run(ActivegymApplication.class, args);
	}

	/**
	 * Provides a {@link ModelMapper} bean for object mapping throughout the application.
	 * <p>
	 * The {@link ModelMapper} is a utility that simplifies the mapping of objects,
	 * mainly used for converting between DTOs and entities.
	 * </p>
	 *
	 * @return A new instance of {@link ModelMapper}.
	 */
	@Bean
	ModelMapper mapper() {
		return new ModelMapper();
	}
}
 