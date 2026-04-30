package com.proyectofinal.huespedes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.proyectofinal.huespedes", "com.proyectofinal.common"})
public class MsvHuespedesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvHuespedesApplication.class, args);
    }
}
