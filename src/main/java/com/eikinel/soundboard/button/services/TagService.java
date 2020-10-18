package com.eikinel.soundboard.button.services;

import com.eikinel.soundboard.button.models.TagDto;
import com.eikinel.soundboard.button.repositories.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagDto> getAllTags() {
        return tagRepository.findAll();
    }

    public TagDto getTagById(String id) {
        return tagRepository.findById(id).orElse(null);
    }

    public TagDto getTagByName(String name) {
        return tagRepository.findByName(name).orElse(null);
    }

    public List<TagDto> getTagsByNames(List<String> names) { return tagRepository.findByNameIn(names); }

    public TagDto create(TagDto tag) {
        return tagRepository.save(TagDto.builder()
                .name(tag.name)
                .build()
        );
    }

    public Page<TagDto> getPagedTagsWithNameContainingSlice(String slice, Integer page, Integer size) {
        return tagRepository.findByNameContainingOrderByNameIgnoreCase(slice, PageRequest.of(page, size));
    }

    public void delete(TagDto tag) {
        tagRepository.delete(tag);
    }
}
