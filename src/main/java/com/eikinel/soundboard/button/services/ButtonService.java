package com.eikinel.soundboard.button.services;

import com.eikinel.soundboard.button.exceptions.ButtonNotFoundException;
import com.eikinel.soundboard.button.models.ButtonDto;
import com.eikinel.soundboard.button.repositories.ButtonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

    public ButtonDto create(ButtonDto button) {
        return buttonRepository.save(ButtonDto.builder()
                .name(button.name)
                .description(button.description)
                .fileName(uploadDir + button.fileName)
                .color(button.color)
                .build()
        );
    }

    public ButtonDto patch(String id, ButtonDto button) {
        ButtonDto buttonToUpdate = buttonRepository.findById(id).orElseThrow(ButtonNotFoundException::new);

        return buttonRepository.save(ButtonDto.builder()
                .id(buttonToUpdate.id)
                .name(StringUtils.isEmpty(button.name) ? buttonToUpdate.name : button.name)
                .description(StringUtils.isEmpty(button.description) ? buttonToUpdate.description : button.description)
                .fileName(uploadDir + (StringUtils.isEmpty(button.fileName) ? buttonToUpdate.fileName : button.fileName))
                .color(StringUtils.isEmpty(button.color) ? buttonToUpdate.color : button.color)
                .build());
    }

    public void delete(ButtonDto button) {
        buttonRepository.delete(button);
    }
}
