package com.eikinel.soundboard.button.controllers;

import com.eikinel.soundboard.button.exceptions.CategoryFieldNotFoundException;
import com.eikinel.soundboard.button.exceptions.CategoryNotFoundException;
import com.eikinel.soundboard.button.models.CategoryDto;
import com.eikinel.soundboard.button.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {

	private final CategoryService categoryService;
	private final Logger logger = LoggerFactory.getLogger(Slf4j.class);

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("")
	public ResponseEntity<List<CategoryDto>> getCategoriesByNames(@RequestParam List<String> names) {
		final List<CategoryDto> categories = categoryService.getCategoriesByNames(names);

		logger.info("Found categories matching names {} -> {}", names, categories);
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		logger.info("List all categories");
		return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<CategoryDto>> getPagedCategoriesWithNameContainingSlice(
					@RequestParam("slice") String slice,
					@RequestParam(value = "page", defaultValue = "0") Integer page,
					@RequestParam(value = "size", defaultValue = "5") Integer size
	) {
		final Page<CategoryDto> categories = this.categoryService.getPagedCategoriesWithNameContainingSlice(slice, page, size);

		logger.info("Found categories -> {}", categories.getContent());
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}
}
