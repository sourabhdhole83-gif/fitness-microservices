package com.activity_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

    @Qualifier("userServiceWebClient")
    private final WebClient userServiceWebClient;

    public boolean validateUser(UUID id) {
        try {
            return userServiceWebClient.get()
                    .uri("/api/users/{id}/validate", id)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        }catch (WebClientException e){
            log.error("User validation failed", e);
            throw e;
        }
    }
}
