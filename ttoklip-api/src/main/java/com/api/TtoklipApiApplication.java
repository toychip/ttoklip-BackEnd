package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.common",
        "com.infrastructure",
        "com.domain",
        "com.api"
})
@SpringBootApplication
public class TtoklipApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TtoklipApiApplication.class, args);
    }
}