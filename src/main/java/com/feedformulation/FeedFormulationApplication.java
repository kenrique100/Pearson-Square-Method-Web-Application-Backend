package com.feedformulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main application class for the Feed Formulation Spring Boot application.
 * This class serves as the entry point and initializes the Spring context.
 */
@SpringBootApplication
public class FeedFormulationApplication {

	/**
	 * The main method which is the entry point of the application.
	 * It uses SpringApplication.run() to launch the application.
	 *
	 * @param args Command line arguments (if any) passed during application startup.
	 */
	public static void main(String[] args) {
		SpringApplication.run(FeedFormulationApplication.class, args);
	}
}
