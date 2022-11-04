package com.project.crazy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrazyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrazyApplication.class, args);
	}

}
