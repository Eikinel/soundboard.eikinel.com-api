package com.eikinel.soundboard.button.controllers;

import com.eikinel.soundboard.button.models.ButtonDto;
import com.eikinel.soundboard.button.services.ButtonService;
import com.eikinel.soundboard.fileManager.services.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/all")
    public ResponseEntity<List<ButtonDto>> getAllButtons() {
        return new ResponseEntity<>(buttonService.getAllButtons(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ButtonDto> getButtonByName(@RequestParam("name") String name) {
        final ButtonDto button = buttonService.getButtonByName(name);

        if (Objects.isNull(button)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(buttonService.getButtonByName(name), HttpStatus.FOUND);
    }

    @PostMapping("")
    public ResponseEntity<ButtonDto> createButton(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("color") String color
    ) {
        final String fileName = fileManagerService.store(file);

        if (StringUtils.isEmpty(fileName)) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(buttonService.create(name, description, fileName, color), HttpStatus.CREATED);
    }
}
