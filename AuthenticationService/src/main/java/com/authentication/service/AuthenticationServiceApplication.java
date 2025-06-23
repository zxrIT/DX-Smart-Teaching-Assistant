package com.authentication.service;

import com.common.component.annotation.EnableAutoCommonComponentBeans;
import com.common.rabbitmq.annotation.EnableAutoCommonRabbitMQBeans;
import com.common.redis.annotation.EnableAutoCommonRedisBeans;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.authentication.service.repository")
@EnableAutoCommonComponentBeans
@EnableAutoCommonRabbitMQBeans
@EnableAutoCommonRedisBeans
public class AuthenticationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }
}
