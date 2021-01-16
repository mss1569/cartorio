package com.mss1569.cartorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class CartorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartorioApplication.class, args);
    }

}
