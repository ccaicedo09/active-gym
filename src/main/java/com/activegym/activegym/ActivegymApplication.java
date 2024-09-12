package com.activegym.activegym;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ActivegymApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivegymApplication.class, args);
	}

	@Bean
	ModelMapper mapper() {
		return new ModelMapper();
	}
}
 