package com.eikinel.soundboard.button.controllers;

import com.eikinel.soundboard.button.models.Button;
import com.eikinel.soundboard.button.services.ButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/button")
public class ButtonController {

    private final ButtonService buttonService;

    @Autowired
    public ButtonController(ButtonService buttonService) {
        this.buttonService = buttonService;
    }

//    @PostMapping("")
//    public ResponseEntity<Button> createButton() {
//
//    }
}
