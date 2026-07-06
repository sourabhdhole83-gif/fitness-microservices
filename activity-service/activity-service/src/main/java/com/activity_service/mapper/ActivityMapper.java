package com.activity_service.mapper;


import com.activity_service.dto.ActivityRequest;
import com.activity_service.dto.ActivityResponse;
import com.activity_service.entity.Activity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    Activity toEntity(ActivityRequest request);
    ActivityResponse toResponse(Activity activity);
}
