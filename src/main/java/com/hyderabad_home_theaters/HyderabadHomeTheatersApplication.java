package com.hyderabad_home_theaters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.hyderabad_home_theaters")
//@EnableJpaRepositories(basePackages = "com.hyderabad_home_theaters.repository")
@EntityScan(basePackages = "com.hyderabad_home_theaters.entity")

public class HyderabadHomeTheatersApplication {
	public static void main(String[] args) {
		SpringApplication.run(HyderabadHomeTheatersApplication.class, args);
	}

}
