package com.activity_service.Repository;

import com.activity_service.entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<Activity,String> {

}
