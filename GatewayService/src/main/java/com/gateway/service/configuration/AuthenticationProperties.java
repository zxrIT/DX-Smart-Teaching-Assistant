package com.gateway.service.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@EnableConfigurationProperties({AuthenticationProperties.class})
@Configuration
@ConfigurationProperties(prefix = "dx.smart.teaching.assistant.auth")
public class AuthenticationProperties {
    private List<String> excludePaths;
}