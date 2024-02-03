package com.eikinel.soundboard.button.services;

import com.eikinel.soundboard.button.models.CategoryDto;
import com.eikinel.soundboard.button.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll();
	}

	public CategoryDto getCategoryById(String id) {
		return categoryRepository.findById(id).orElse(null);
	}

	public CategoryDto getCategoryByName(String name) {
		return categoryRepository.findByName(name).orElse(null);
	}

	public List<CategoryDto> getCategoriesByNames(List<String> names) {
		return categoryRepository.findByNameIn(names);
	}

	public CategoryDto create(String category) {
		return categoryRepository.save(CategoryDto.builder()
						.name(category)
						.build()
		);
	}

	public Page<CategoryDto> getPagedCategoriesWithNameContainingSlice(String slice, Integer page, Integer size) {
		return categoryRepository.findByNameContainingOrderByNameIgnoreCase(slice, PageRequest.of(page, size));
	}

	public void delete(String name) {
		final Optional<CategoryDto> category = categoryRepository.findByName(name);

		category.ifPresent(categoryRepository::delete);
	}
}
