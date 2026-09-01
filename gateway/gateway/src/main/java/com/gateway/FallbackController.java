package com.gateway;
//i have configured in the properties like if the cb is open so go to this uri fallback

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user")
    public ResponseEntity<Map<String,Object>>  userServiceFallbackResponse(){

        Map<String,Object> response = Map.of(  //user errordto instead of this
          "status",503,
                "message","Service not available",
                "service","USER_SERVICE"
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(response);
    }
}
