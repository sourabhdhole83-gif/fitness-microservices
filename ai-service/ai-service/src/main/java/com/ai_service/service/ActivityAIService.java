package com.ai_service.service;

//this service will process the AI responses

import com.ai_service.dto.GeminiResponseDtos.Content;
import com.ai_service.dto.GeminiResponseDtos.GeminiResponse;
import com.ai_service.dto.GeminiResponseDtos.Step;
import com.ai_service.dto.RecommendationResponse;
import com.ai_service.entity.Activity;
import com.ai_service.entity.Recommendation;
import com.ai_service.repository.RecommendationRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;
    private final RecommendationRepo recommendationRepo;

    public Recommendation generateRecommendation(Activity activity) {

        String prompt = createPromptForActivity(activity);

        GeminiResponse geminiResponse =
                geminiService.getRecommendations(prompt);

        log.info("Gemini Response : {}", geminiResponse);

        String responseText = extractModelOutput(geminiResponse);//Extracts the actual JSON text from Gemini response.

        try {

            RecommendationResponse aiResponse =   //Now JSON becomes a Java object.
                    objectMapper.readValue(
                            responseText,
                            RecommendationResponse.class
                    );

            Recommendation recommendation =
                    buildRecommendation(activity, aiResponse);


            Recommendation saved = recommendationRepo.save(recommendation);
            return saved;

        } catch (Exception e) {

            log.error("Failed to process AI response", e);

            throw new RuntimeException("AI processing failed");
        }
    }

    private Recommendation buildRecommendation(
            Activity activity,
            RecommendationResponse aiResponse) {

        try{
            Recommendation recommendation = new Recommendation();

            recommendation.setUserId(activity.getUserId());
             recommendation.setActivityId(activity.getId());

         recommendation.setRecommendation(
            aiResponse.getRecommendation());

         recommendation.setImprovement(
            aiResponse.getImprovement());

         recommendation.setSuggestions(
            aiResponse.getSuggestions());

         recommendation.setSafety(
            aiResponse.getSafety());

            return  recommendation;

        }catch (Exception e){
            e.printStackTrace();
            return createDefaultRecommendation(activity);
}
    }

    public Recommendation createDefaultRecommendation(Activity activity){
        return Recommendation.builder()
               .activityId(activity.getId())
               .userId(activity.getUserId())
               .recommendation("Unable to generate detailed analysis")
               .improvement(Collections.singletonList("Continue with your current routine"))
               .suggestions(Collections.singletonList("Consider consulting a fitness professional"))
               .safety(Arrays.asList(
                       "Always warm up before exercise",
                       "Stay hydrated",
                       "Listen to your body"))
               .build();
    }

    private String extractModelOutput(GeminiResponse response) { //Extracts and returns the actual AI-generated text (model output) from the GeminiResponse object.

        for (Step step : response.getSteps()) {

            if ("model_output".equals(step.getType())) {

                Content content =
                        step.getContent().get(0);

                return content.getText();
            }
        }

        throw new RuntimeException(
                "Model output not found");
    }


    private String createPromptForActivity(Activity activity) {

        return String.format("""
                        
                        Analyze the following fitness activity and provide recommendations.
                        
                        IMPORTANT RULES:
                        1. Return ONLY valid JSON.
                        2. Do NOT return markdown.
                        3. Do NOT return explanation.
                        4. Do NOT return code blocks.
                        5. Do NOT include any text outside the JSON.
                        6. Follow the JSON structure EXACTLY.
                        
                        Expected JSON Format:
                        
                        {
                          "recommendation": "Overall recommendation for the activity",
                        
                          "improvement": [
                            "Improvement point 1",
                            "Improvement point 2"
                          ],
                        
                          "suggestions": [
                            "Workout suggestion 1",
                            "Workout suggestion 2"
                          ],
                        
                          "safety": [
                            "Safety point 1",
                            "Safety point 2"
                          ]
                        }
                        
                        Activity Details:
                        
                        Activity Type: %s
                        Duration: %d minutes
                        Calories Burned: %d
                        Additional Metrics: %s
                        
                        Provide:
                        - One overall recommendation.
                        - At least 3 improvement points.
                        - At least 3 workout suggestions.
                        - At least 3 safety recommendations.
                        
                        """,

                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }
}

