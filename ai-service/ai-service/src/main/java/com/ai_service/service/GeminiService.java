package com.ai_service.service;

import com.ai_service.dto.GeminiResponseDtos.GeminiResponse;
import com.ai_service.exception.AIServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
@Slf4j
public class GeminiService {
    private WebClient webClient;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;


    public GeminiService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.build();
    }

    public GeminiResponse getRecommendations(String prompt){
        Map<String, Object> requestBody = Map.of(
                "model", "gemini-3.5-flash",   //body for postman
                "input", prompt);

        try {
            return webClient.post()
                    .uri(geminiApiUrl)
                    .header("x-goog-api-key", geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)//  in the post req i have to send body and the gemini also needs the body
                    .retrieve()    //Clicking SEND button in Postman

//                        .onStatus(
//                        status -> status.value() == 429,
//                        clientResponse -> clientResponse
//                                .bodyToMono(String.class)
//                                .flatMap(errorBody -> {
//                                    log.error("Gemini 429 Error: {}", errorBody);
//                                    return Mono.error(
//                                            new RuntimeException(errorBody)
//                                    );
//                                }))

                    .bodyToMono(GeminiResponse.class)//what the body is coming converts it into: GeminiResponse
                    .block();

        }catch(WebClientResponseException e){
            log.error("Gemini API error",e);

            throw new AIServiceException("AI service is temporarily unavailable",e);
        }


    }
}
