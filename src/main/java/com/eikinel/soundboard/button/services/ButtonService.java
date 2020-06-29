package com.eikinel.soundboard.button.services;

import com.eikinel.soundboard.button.models.ButtonDto;
import com.eikinel.soundboard.button.repositories.ButtonRepository;
import org.springframework.stereotype.Service;

@Service
public class ButtonService {

    private final ButtonRepository buttonRepository;

    public ButtonService(ButtonRepository buttonRepository) {
        this.buttonRepository = buttonRepository;
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
