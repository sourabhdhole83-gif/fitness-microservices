package com.activity_service.service;

import com.activity_service.Repository.ActivityRepository;
import com.activity_service.dto.ActivityRequest;
import com.activity_service.dto.ActivityResponse;
import com.activity_service.entity.Activity;
import com.activity_service.mapper.ActivityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private ActivityRepository repository;
    private ActivityMapper mapper;


    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {

        Activity activity = mapper.toEntity(request);

        Activity saved = repository.save(activity);

        ActivityResponse response = mapper.toResponse(saved);

        return response;
    }
}
