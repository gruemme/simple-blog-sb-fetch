package com.example.mySimpleBlog.controller;

import com.example.mySimpleBlog.dto.TagCount;
import com.example.mySimpleBlog.model.Entry;
import com.example.mySimpleBlog.repositories.TagRepository;
import com.example.mySimpleBlog.service.EntryService;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    private final EntryService entryService;
    private final TagRepository tagRepository;

    public TagController(EntryService entryService, TagRepository tagRepository) {
        this.entryService = entryService;
        this.tagRepository = tagRepository;
    }

    @GetMapping(path = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagCount> getAllTags() {
        return tagRepository.findTagCount();
    }

    @GetMapping(path = "/tag/{tagname}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public List<Entry> getEntriesByTag(@PathVariable("tagname") String tagname) {
        return entryService.getEntriesByTag(tagname);
    }
}
