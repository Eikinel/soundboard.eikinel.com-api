package com.eikinel.soundboard.button.services;

import com.eikinel.soundboard.button.exceptions.ButtonNotFoundException;
import com.eikinel.soundboard.button.models.ButtonDto;
import com.eikinel.soundboard.button.models.CategoryDto;
import com.eikinel.soundboard.button.repositories.ButtonRepository;
import com.eikinel.soundboard.button.services.CategoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ButtonService {

	@Value("${file.upload-dir}")
	private final String uploadDir = "";
	private final ButtonRepository buttonRepository;
	private final CategoryService categoryService;

	public ButtonService(
					ButtonRepository buttonRepository,
					CategoryService categoryService) {
		this.buttonRepository = buttonRepository;
		this.categoryService = categoryService;
	}

	public List<ButtonDto> getAllButtons() {
		return buttonRepository.findAll();
	}

	public ButtonDto getButtonByName(String name) {
		return buttonRepository.findByName(name).orElse(null);
	}

	public List<ButtonDto> getButtonsByCategory(String category) {
		return buttonRepository.findByCategory(category).orElse(null);
	}

	public ButtonDto getButtonById(String id) {
		return buttonRepository.findById(id).orElse(null);
	}

	public ButtonDto create(ButtonDto button) {
		final CategoryDto category = categoryService.getCategoryByName(button.category);

		if (StringUtils.isEmpty(category)) {
			categoryService.create(button.category);
		}

		return buttonRepository.save(ButtonDto.builder()
						.name(button.name)
						.category(button.category)
						.description(button.description)
						.tags(button.tags)
						.fileName(uploadDir + button.fileName)
						.color(button.color)
						.gain(button.gain)
						.build()
		);
	}

	public ButtonDto patch(String id, ButtonDto button) {
		final ButtonDto buttonToUpdate = buttonRepository.findById(id).orElseThrow(ButtonNotFoundException::new);
		final CategoryDto category = categoryService.getCategoryByName(button.category);

		if (StringUtils.isEmpty(category)) {
			categoryService.create(button.category);
		}

		final ButtonDto createdButton = buttonRepository.save(ButtonDto.builder()
						.id(buttonToUpdate.id)
						.name(StringUtils.isEmpty(button.name) ? buttonToUpdate.name : button.name)
						.category(StringUtils.isEmpty(button.category) ? buttonToUpdate.category : button.category)
						.description(StringUtils.isEmpty(button.description) ? buttonToUpdate.description : button.description)
						.tags(button.tags)
						.fileName(uploadDir + (StringUtils.isEmpty(button.fileName) ? buttonToUpdate.fileName : button.fileName))
						.color(StringUtils.isEmpty(button.color) ? buttonToUpdate.color : button.color)
						.gain(StringUtils.isEmpty(button.gain) ? buttonToUpdate.gain : button.gain)
						.build());

		if (ObjectUtils.isEmpty(this.getButtonsByCategory(buttonToUpdate.category))) {
			categoryService.delete(buttonToUpdate.category);
		}

		return createdButton;
	}

	public void delete(ButtonDto button) {
		buttonRepository.delete(button);

		if (ObjectUtils.isEmpty(this.getButtonsByCategory(button.category))) {
			categoryService.delete(button.category);
		}
	}
}
