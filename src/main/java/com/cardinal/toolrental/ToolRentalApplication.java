package com.cardinal.toolrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
@Import(GlobalExceptionHandler.class)
public class ToolRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolRentalApplication.class, args);
	}

}