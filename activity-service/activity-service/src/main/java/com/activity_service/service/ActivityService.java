package com.activity_service.service;

import com.activity_service.dto.ActivityRequest;
import com.activity_service.dto.ActivityResponse;

public interface ActivityService {

     ActivityResponse trackActivity(ActivityRequest request);
}
