package com.example.classservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
public class ClassServiceApplication {

	@Bean
	public WebClient webClient(){
		return WebClient.builder().build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ClassServiceApplication.class, args);
	}

}
