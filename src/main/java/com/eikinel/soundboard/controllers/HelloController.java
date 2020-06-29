package com.eikinel.soundboard.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
class HelloController {

    @GetMapping("")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Greetings !", HttpStatus.OK);
    }
}