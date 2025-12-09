package com.giuliopastore.BankApp;

import me.paulschwarz.springdotenv.DotenvPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class BankAppApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BankAppApplication.class);
		ConfigurableEnvironment environment = new StandardEnvironment();
		DotenvPropertySource.addToEnvironment(environment);
		application.setEnvironment(environment);
		application.run(args);
	}

}
