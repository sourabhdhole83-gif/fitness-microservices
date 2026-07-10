package com.ai_service.service;

import com.ai_service.entity.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;

    @KafkaListener(topics = "${kafka.topic.name}",groupId = "activity-processor-group")
    public void processActivity(Activity activity){
        log.info("Message received with id: "+activity.getUserId());
        log.info("Activity Received : {}", activity);
        activityAIService.generateRecommendation(activity);
    }
}
