package com.eikinel.soundboard.button.services;

import com.eikinel.soundboard.button.models.ButtonDto;
import com.eikinel.soundboard.button.repositories.ButtonRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ButtonService {

    @Value("${file.upload-dir}")
    private final String uploadDir = "";
    private final ButtonRepository buttonRepository;

    public ButtonService(ButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
    }

    public List<ButtonDto> getAllButtons() {
        return buttonRepository.findAll();
    }

    public ButtonDto getButtonByName(String name) {
        return buttonRepository.findByName(name).orElse(null);
    }

    public ButtonDto getButtonById(String id) {
        return buttonRepository.findById(id).orElse(null);
    }

    public ButtonDto create(String name, String description, String fileName, String color) {
        return buttonRepository.save(ButtonDto.builder()
                .name(name)
                .description(description)
                .fileName(uploadDir + fileName)
                .color(color)
                .build()
        );
    }

    public void delete(ButtonDto button) {
        buttonRepository.delete(button);
    }
}
