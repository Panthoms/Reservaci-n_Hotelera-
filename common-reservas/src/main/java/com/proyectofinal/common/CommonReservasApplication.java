package com.proyectofinal.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CommonReservasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonReservasApplication.class, args);
	}

}
