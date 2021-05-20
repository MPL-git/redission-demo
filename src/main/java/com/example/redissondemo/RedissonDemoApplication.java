package com.example.redissondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan({"com.example", "cn.hutool"})
@SpringBootApplication
public class RedissonDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedissonDemoApplication.class, args);
    }

}
