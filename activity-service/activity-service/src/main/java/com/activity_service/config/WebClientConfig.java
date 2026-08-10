package com.activity_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // 1. Plain Builder marked @Primary
    // Spring Boot Admin will use THIS builder (bypasses the Load Balancer for localhost)
    @Bean
    @Primary
    public WebClient.Builder plainWebClientBuilder() {
        return WebClient.builder();
    }

    // 2. Load-Balanced Builder
    // Used ONLY when explicitly called by bean name "lbBuilder"
    @Bean("lbBuilder")
    @LoadBalanced
    public WebClient.Builder loadBalancedBuilder() {
        return WebClient.builder();
    }

    // 3. WebClient for your microservice-to-microservice calls
    @Bean("userServiceWebClient")
    public WebClient userServiceWebClient(@Qualifier("lbBuilder") WebClient.Builder builder) {
        return builder.baseUrl("http://USER-SERVICE").build();
    }
}