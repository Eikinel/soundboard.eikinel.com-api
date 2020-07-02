package com.eikinel.soundboard.button.controllers;

import com.eikinel.soundboard.button.exceptions.ButtonFieldNotFoundException;
import com.eikinel.soundboard.button.exceptions.ButtonNotFoundException;
import com.eikinel.soundboard.button.models.ButtonDto;
import com.eikinel.soundboard.button.models.ButtonPayload;
import com.eikinel.soundboard.button.services.ButtonService;
import com.eikinel.soundboard.fileManager.services.FileManagerService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/button")
public class ButtonController {

    private final ButtonService buttonService;
    private final FileManagerService fileManagerService;
    private final Logger logger = LoggerFactory.getLogger(Slf4j.class);

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
    public ResponseEntity<ButtonDto> getButtonById(@RequestParam("id") String id) {
        final ButtonDto button = buttonService.getButtonById(id);

        if (ObjectUtils.isEmpty(button)) {
            logger.error("Button with id {} not found", id);
            throw new ButtonNotFoundException(HttpStatus.NOT_FOUND, "Button not found");
        }

        logger.info("Found button -> {}", button);
        return new ResponseEntity<>(button, HttpStatus.FOUND);
    }

//    @PostMapping("")
//    public ResponseEntity<ButtonDto> createButton(@ModelAttribute ButtonPayload payload) {
//        if (StringUtils.isEmpty(payload.file)) {
//            logger.error("Required field 'file' does not exist. Payload -> {}", payload);
//            throw new ButtonFieldNotFoundException(HttpStatus.BAD_REQUEST, "Required field 'file' does not exist");
//        }
//
//        String fileName;
//
//        try {
//            fileName = fileManagerService.store(payload.file);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            throw e;
//        }
//
//        final ButtonDto createdButton = buttonService.create(payload.name, payload.description, fileName, payload.color);
//
//        logger.info("Created button -> {}", createdButton);
//        return new ResponseEntity<>(createdButton, HttpStatus.CREATED);
//    }

    @PostMapping("")
    public ResponseEntity<ButtonDto> createButtonByFileName(@RequestBody ButtonDto button) {
        if (StringUtils.isEmpty(button.fileName)) {
            logger.error("Required field 'fileName' does not exist. Button -> {}", button);
            throw new ButtonFieldNotFoundException(HttpStatus.BAD_REQUEST, "Required field 'fileName' does not exist");
        }

        final ButtonDto createdButton = buttonService.create(button.name, button.description, button.fileName, button.color);

        logger.info("Created button -> {}", createdButton);
        return new ResponseEntity<>(createdButton, HttpStatus.CREATED);
    }

    @DeleteMapping("")
    public ResponseEntity<ButtonDto> deleteButton(@RequestParam("id") String id) {
        final ButtonDto button = buttonService.getButtonById(id);

        if (ObjectUtils.isEmpty(button)) {
            logger.error("Button with id {} not found", id);
            throw new ButtonNotFoundException(HttpStatus.NOT_FOUND, "Button not found");
        }

        buttonService.delete(button);

        logger.info("Deleted button -> {}", button);
        return new ResponseEntity<>(button, HttpStatus.OK);
    }
}
