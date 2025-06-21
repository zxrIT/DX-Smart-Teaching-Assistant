package com.talent.training.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TalentTrainingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TalentTrainingServiceApplication.class, args);
    }
}
