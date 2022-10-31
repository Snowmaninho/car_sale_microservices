package com.snowman.main_auto_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.snowman.*")
@EnableJpaRepositories("com.snowman.*")
@ComponentScan(basePackages = { "com.snowman.*" })
@EntityScan("com.snowman.*")
public class MainAutoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainAutoServiceApplication.class, args);
        System.out.println("CHECK!");
    }

}
