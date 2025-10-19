package com.authService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // enable auto config + component scanning
public class App {
    public static void main(String[] args) {
        System.out.println("Something");
        SpringApplication.run(App.class);
    }
}
