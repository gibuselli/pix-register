package com.gibuselli.pix_register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PixRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PixRegisterApplication.class, args);
	}

}
