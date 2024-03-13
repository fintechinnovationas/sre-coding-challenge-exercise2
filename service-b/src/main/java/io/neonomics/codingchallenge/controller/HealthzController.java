package io.neonomics.codingchallenge.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class HealthzController {

    @GetMapping(value = "/healthz")
    public ResponseEntity<?> healthz() throws Exception {
        return new ResponseEntity<String>(
                "OK", HttpStatus.OK);
    }

}
