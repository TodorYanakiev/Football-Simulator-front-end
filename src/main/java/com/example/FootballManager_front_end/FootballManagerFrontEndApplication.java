package com.example.FootballManager_front_end;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FootballManagerFrontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballManagerFrontEndApplication.class, args);
	}

}
