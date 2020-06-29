package com.eikinel.soundboard.button.services;

import com.eikinel.soundboard.button.models.ButtonDto;
import com.eikinel.soundboard.button.repositories.ButtonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ButtonService {

    private final ButtonRepository buttonRepository;

    public ButtonService(ButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
    }

    public List<ButtonDto> getAllButtons() {
        return buttonRepository.findAll();
    }

    public ButtonDto getButtonByName(String name) {
        return buttonRepository.findByName(name);
    }

    public ButtonDto create(String name, String description, String fileName, String color) {
        return buttonRepository.save(ButtonDto.builder()
                .name(name)
                .description(description)
                .fileName(fileName)
                .color(color)
                .build()
        );
    }
}
