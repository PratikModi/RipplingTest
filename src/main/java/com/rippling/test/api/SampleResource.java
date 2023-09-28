package com.rippling.test.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleResource {

    @RequestMapping(value = "/v1/test")
    ResponseEntity<String> testApplication(){
        return ResponseEntity.ok("Application Is Running!!");
    }

}
