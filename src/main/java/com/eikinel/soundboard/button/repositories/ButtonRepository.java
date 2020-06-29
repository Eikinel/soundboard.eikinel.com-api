package com.eikinel.soundboard.button.repositories;

import com.eikinel.soundboard.button.models.ButtonDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ButtonRepository extends MongoRepository<ButtonDto, String> {
    ButtonDto findByName(String name);
}
