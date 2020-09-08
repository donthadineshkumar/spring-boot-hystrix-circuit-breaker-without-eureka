package com.cb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/*
This project interacts with a web project
spring-boot-ribbon-provider
has an end-point /recommended - recommended books list

@EnableCircuitBreaker - to enable the hystrix circuit breaker
@EnableHystrixDashboard - http://localhost:8080/hystrix opens up a hystrix dashboard
@HystrixCommand on a method controls the circuit breaker.

@EnableTurbine - to acculate all the applicatons hystrix dashboards
into a turbine - better used with multiple apps, whereas hystrix
dashboard is fine for a single app, revisted again

Turbine works with eureka server well, it configuration
has the service ids (as declared in eureka server)

To see the hystrix dashboard, you need @EnableHystrixDashboard
& the following config

management:
  endpoints:
    web:
      base-path: /actuator
      exposure:
        include: hystrix.stream

hystrix:
  dashboard:
    proxy-stream-allow-list: "*"

 */
@SpringBootApplication
@RestController
@EnableCircuitBreaker
@EnableHystrixDashboard
//@EnableTurbine
public class SpringBootHystrixCircuitBreakerApplication {

	@Autowired
	BookService bookService;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHystrixCircuitBreakerApplication.class, args);
	}

	@GetMapping("/to-read")
	public String toRead() {
		return bookService.readingList();
	}

}
