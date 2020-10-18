package com.eikinel.soundboard.button.controllers;

import com.eikinel.soundboard.button.exceptions.TagFieldNotFoundException;
import com.eikinel.soundboard.button.exceptions.TagNotFoundException;
import com.eikinel.soundboard.button.models.TagDto;
import com.eikinel.soundboard.button.services.TagService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;
    private final Logger logger = LoggerFactory.getLogger(Slf4j.class);

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("")
    public ResponseEntity<List<TagDto>> getTagsByNames(@RequestParam List<String> names) {
        final List<TagDto> tags = tagService.getTagsByNames(names);

        logger.info("Found tags matching names {} -> {}", names, tags);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagDto>> getAllTags() {
        logger.info("List all tags");
        return new ResponseEntity<>(tagService.getAllTags(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TagDto>> getPagedTagsWithNameContainingSlice(
            @RequestParam("slice") String slice,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        final Page<TagDto> tags = this.tagService.getPagedTagsWithNameContainingSlice(slice, page, size);

        logger.info("Found tags -> {}", tags.getContent());
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<List<TagDto>> createTags(@RequestBody List<TagDto> tags) {
        if (ObjectUtils.isEmpty(tags)) {
            logger.error("Tags field is empty");
            throw new TagNotFoundException(HttpStatus.ACCEPTED, "Tags field is empty");
        }

        if (tags.stream().anyMatch(tag -> StringUtils.isEmpty(tag.name))) {
            logger.error("Required field 'name' does not exist in a tag. Tags -> {}", tags);
            throw new TagFieldNotFoundException(HttpStatus.BAD_REQUEST, "Required field 'name' does not exist in a tag");
        }

        List<TagDto> createdTags = tags.stream()
                .filter(tag -> ObjectUtils.isEmpty(tagService.getTagByName(tag.name)))
                .map(tagService::create)
                .collect(Collectors.toList());

        logger.info("Created tags -> {}", createdTags);
        return new ResponseEntity<>(createdTags, ObjectUtils.isEmpty(createdTags) ? HttpStatus.OK : HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TagDto> deleteTag(@PathVariable String id) {
        final TagDto tag = tagService.getTagById(id);

        if (ObjectUtils.isEmpty(tag)) {
            logger.error("Tag with id {} not found", id);
            throw new TagNotFoundException(HttpStatus.NOT_FOUND, "Tag not found");
        }

        tagService.delete(tag);

        logger.info("Deleted tag -> {}", tag);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }
}
