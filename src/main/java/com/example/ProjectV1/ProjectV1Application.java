package com.example.ProjectV1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectV1Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjectV1Application.class, args);
	}
	/*@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}*/

}
