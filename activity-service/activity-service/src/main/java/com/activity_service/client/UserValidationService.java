package com.activity_service.client;

import com.activity_service.exception.RateLimiterException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

    @Qualifier("userServiceWebClient")
    private final WebClient userServiceWebClient;

    @RateLimiter(name = "activityLimiter",fallbackMethod = "rateLimiterFallback")
    @Retry(name = "userServiceRetry",fallbackMethod = "userServiceFallback")
    @CircuitBreaker(name = "userServiceCB",fallbackMethod = "userServiceFallback")
    public boolean validateUser(UUID id) {
            return userServiceWebClient.get()
                    .uri("/api/users/{id}/validate", id)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

    }

    public boolean userServiceFallback(UUID id, Exception ex) {
        log.error("User Service is down", ex);
        return false;
    }

    public boolean rateLimiterFallback(UUID id, RequestNotPermitted ex){
        throw new RateLimiterException("Too many requests , Try again later");
    }


}

