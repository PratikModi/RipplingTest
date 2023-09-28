package com.rippling.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RipplingTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RipplingTestApplication.class, args);
    }

}
