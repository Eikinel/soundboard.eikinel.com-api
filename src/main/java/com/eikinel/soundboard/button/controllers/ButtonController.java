package com.eikinel.soundboard.button.controllers;

import com.eikinel.soundboard.button.models.ButtonDto;
import com.eikinel.soundboard.button.services.ButtonService;
import com.eikinel.soundboard.fileManager.services.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/button")
public class ButtonController {

    private final ButtonService buttonService;
    private final FileManagerService fileManagerService;

    @Autowired
    public ButtonController(
            ButtonService buttonService,
            FileManagerService fileManagerService) {
        this.buttonService = buttonService;
        this.fileManagerService = fileManagerService;
    }

    @PostMapping("")
    public ResponseEntity<ButtonDto> createButton(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("color") String color
    ) {
        final String fileName = fileManagerService.store(file);

        return new ResponseEntity<>(buttonService.create(name, description, fileName, color), HttpStatus.OK);
    }
}
