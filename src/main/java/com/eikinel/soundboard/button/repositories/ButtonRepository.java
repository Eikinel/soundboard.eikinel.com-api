package com.eikinel.soundboard.button.repositories;

import com.eikinel.soundboard.button.models.ButtonDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ButtonRepository extends MongoRepository<ButtonDto, String> {
    Optional<ButtonDto> findByName(String name);
    Optional<ButtonDto> findById(String id);
}
