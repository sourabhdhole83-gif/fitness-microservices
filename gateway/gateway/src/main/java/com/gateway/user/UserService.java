package com.gateway.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Qualifier("userServiceWebClient")
    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String id) {

            return userServiceWebClient.get()
                    .uri("/api/users/{id}/validate", id)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .onErrorResume(WebClientResponseException.class
                            ,e->{

                        if(e.getStatusCode()== HttpStatus.NOT_FOUND)
                            return Mono.error(new RuntimeException("user not found with id: "+id));

                        else if (e.getStatusCode()==HttpStatus.BAD_REQUEST)
                            return Mono.error(new RuntimeException("Invalid: "+id));

                        return Mono.error(new RuntimeException("Unexpected error: "+id));
                            });
    }

    public Mono<UserResponse> registerUser(RegisterRequest registerRequest) {
        log.info("Calling User registration for {}",registerRequest.getEmail());

        return userServiceWebClient.post()
                .uri("/api/users/register")
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class
                        ,e->{

                             if (e.getStatusCode()==HttpStatus.BAD_REQUEST)
                                return Mono.error(new RuntimeException("Bad Request: "+e.getMessage()));

                            return Mono.error(new RuntimeException("Unexpected error: "+e.getMessage()));
                        });

    }
}
