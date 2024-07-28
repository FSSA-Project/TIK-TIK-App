package com.fssa.todo;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class TodoAppApiApplication {
    public static void main(String[] args) {

        /**
         * Below the code for check the dotenv for run
         * the locally
         */
//        Dotenv dotenv = Dotenv.load();
//        // Set environment variables
//        System.setProperty("DB_URL", dotenv.get("DB_URL"));
//        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
//        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
//        System.setProperty("JWT_SECRET",dotenv.get("JWT_SECRET"));

        SpringApplication.run(TodoAppApiApplication.class, args);

    }

}
