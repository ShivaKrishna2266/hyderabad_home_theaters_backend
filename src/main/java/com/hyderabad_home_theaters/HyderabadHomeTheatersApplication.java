package com.hyderabad_home_theaters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.hyderabad_home_theaters")
public class HyderabadHomeTheatersApplication {
	public static void main(String[] args) {
		SpringApplication.run(HyderabadHomeTheatersApplication.class, args);
	}

}
