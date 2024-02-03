package com.eikinel.soundboard.button.controllers;

import com.eikinel.soundboard.button.exceptions.ButtonFieldNotFoundException;
import com.eikinel.soundboard.button.exceptions.ButtonNotFoundException;
import com.eikinel.soundboard.button.models.ButtonDto;
import com.eikinel.soundboard.button.services.ButtonService;
import com.eikinel.soundboard.button.services.CategoryService;
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
	private final CategoryService categoryService;
	private final Logger logger = LoggerFactory.getLogger(Slf4j.class);

	@Autowired
	public ButtonController(ButtonService buttonService, CategoryService categoryService) {
		this.buttonService = buttonService;
		this.categoryService = categoryService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<ButtonDto>> getAllButtons() {
		logger.info("List all buttons");
		return new ResponseEntity<>(buttonService.getAllButtons(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ButtonDto> getButtonById(@PathVariable String id) {
		final ButtonDto button = buttonService.getButtonById(id);

		if (ObjectUtils.isEmpty(button)) {
			logger.error("Button with id {} not found", id);
			throw new ButtonNotFoundException(HttpStatus.NOT_FOUND, "Button not found");
		}

		logger.info("Found button -> {}", button);
		return new ResponseEntity<>(button, HttpStatus.FOUND);
	}

	@PostMapping("")
	public ResponseEntity<ButtonDto> createButton(@RequestBody ButtonDto button) {
		if (StringUtils.isEmpty(button.fileName)) {
			logger.error("Required field 'fileName' does not exist. Button -> {}", button);
			throw new ButtonFieldNotFoundException(HttpStatus.BAD_REQUEST, "Required field 'fileName' does not exist");
		}

		final ButtonDto createdButton = buttonService.create(button);

		logger.info("Created button -> {}", createdButton);
		return new ResponseEntity<>(createdButton, HttpStatus.CREATED);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ButtonDto> patchButtonById(@PathVariable String id, @RequestBody ButtonDto button) {
		final ButtonDto patchedButton = buttonService.patch(id, button);

		if (ObjectUtils.isEmpty(buttonService.getButtonsByCategory(button.category))) {
			categoryService.delete(button.category);
		}

		logger.info("Patched button -> {}", patchedButton);
		return new ResponseEntity<>(patchedButton, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ButtonDto> deleteButtonById(@PathVariable String id) {
		final ButtonDto button = buttonService.getButtonById(id);

		if (ObjectUtils.isEmpty(button)) {
			logger.error("Button with id {} not found", id);
			throw new ButtonNotFoundException(HttpStatus.NOT_FOUND, "Button not found");
		}

		buttonService.delete(button);

		if (ObjectUtils.isEmpty(buttonService.getButtonsByCategory(button.category))) {
			categoryService.delete(button.category);
		}

		logger.info("Deleted button -> {}", button);
		return new ResponseEntity<>(button, HttpStatus.OK);
	}
}
