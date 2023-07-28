package com.xam.quizservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients
public class QuizServiceApplication {

//	@Value("${question.service.url}")
//	public String questionSeviceUrl;

	public static void main(String[] args) {
		SpringApplication.run(QuizServiceApplication.class, args);
	}

	@Bean
	public WebClient webClient() {
		WebClient webClient = WebClient.builder().baseUrl("http://192.168.0.135:8081/question").build();
		return webClient;
	}
}
