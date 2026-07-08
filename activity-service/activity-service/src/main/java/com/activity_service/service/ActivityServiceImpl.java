package com.activity_service.service;

import com.activity_service.Repository.ActivityRepository;
import com.activity_service.dto.ActivityRequest;
import com.activity_service.dto.ActivityResponse;
import com.activity_service.entity.Activity;
import com.activity_service.exception.ResourceNotFoundException;
import com.activity_service.mapper.ActivityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository repository;
    private final ActivityMapper mapper;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String,Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {

        boolean validatedUser = userValidationService.validateUser(request.getUserId());

        log.info("validatedUser {}",validatedUser);


        if(!validatedUser){
            throw new ResourceNotFoundException("User not found with userId: "+request.getUserId());
        }

        Activity activity = mapper.toEntity(request);

        Activity saved = repository.save(activity);

        try {
            kafkaTemplate.send(topicName, saved.getUserId().toString(),saved);
        }catch (Exception e){
            e.printStackTrace();
        }

        ActivityResponse response = mapper.toResponse(saved);

        return response;
    }
}
