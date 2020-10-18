package com.eikinel.soundboard.button.repositories;

import com.eikinel.soundboard.button.models.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends MongoRepository<TagDto, String> {
    Optional<TagDto> findByName(String name);
    List<TagDto> findByNameIn(List<String> name);

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    Page<TagDto> findByNameContainingOrderByNameIgnoreCase(String slice, Pageable pageable);
}
