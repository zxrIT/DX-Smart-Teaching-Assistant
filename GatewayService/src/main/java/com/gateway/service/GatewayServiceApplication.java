package com.gateway.service;

import com.common.component.annotation.EnableAutoCommonComponentBeans;
import com.common.rabbitmq.annotation.EnableAutoCommonRabbitMQBeans;
import com.common.redis.annotation.EnableAutoCommonRedisBeans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoCommonRabbitMQBeans
@EnableAutoCommonComponentBeans
@EnableAutoCommonRedisBeans
public class GatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
