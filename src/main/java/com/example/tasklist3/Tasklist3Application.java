package com.example.tasklist3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class Tasklist3Application {

    public static void main(final String[] args) {
        SpringApplication.run(Tasklist3Application.class, args);
    }

}
