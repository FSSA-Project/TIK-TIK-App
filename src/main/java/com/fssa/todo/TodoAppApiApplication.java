package com.fssa.todo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoAppApiApplication {
    public static void main(String[] args) {

        /**
         * Below the code for check the dotenv for run
         * the locally
         */

        // Set environment variables
//        Dotenv dotenv = Dotenv.load();
//        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
//        System.setProperty("GOOGLE_ClIENT_ID", dotenv.get("GOOGLE_ClIENT_ID"));
//        System.setProperty("GOOGLE_SECRET_ID", dotenv.get("GOOGLE_SECRET_ID"));

        SpringApplication.run(TodoAppApiApplication.class, args);

    }

}
