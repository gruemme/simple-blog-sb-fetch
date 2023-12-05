package com.example.mySimpleBlog.service;

import com.example.mySimpleBlog.dto.EntryInput;
import com.example.mySimpleBlog.model.BlogUser;
import com.example.mySimpleBlog.model.Entry;
import com.example.mySimpleBlog.model.Image;
import com.example.mySimpleBlog.model.Tag;
import com.example.mySimpleBlog.repositories.EntryRepository;
import com.example.mySimpleBlog.repositories.TagRepository;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EntryService {

  private final EntryRepository entryRepository;
  private final TagRepository tagRepository;

  public EntryService(EntryRepository entryRepository, TagRepository tagRepository) {
    this.entryRepository = entryRepository;
    this.tagRepository = tagRepository;
  }

  public Page<Entry> getAllEntries(Pageable pageable) {
    return entryRepository.findAll(pageable);
  }

  public Optional<Entry> findById(Long entryId) {
    return entryRepository.findById(entryId);
  }

  public List<Entry> getEntriesByAuthor(BlogUser author, Pageable pageable) {
    return entryRepository.findByAuthor(author, pageable);
  }

  public List<Entry> getEntriesByTag(String tagName) {
    return entryRepository.findByTags_NameOrderByCreatedDesc(tagName);
  }

  public Entry createEntryForUser(EntryInput entryInput, BlogUser author) {
    Image titleImage = createImageIfExists(entryInput);
    Set<Tag> tags = getOrCreateTags(entryInput);

    Entry newEntryToSave =
        new Entry(entryInput.getTitle(), entryInput.getText(), titleImage, author, tags);
    tags.stream().forEach(t -> t.getEntries().add(newEntryToSave));

    Entry entrySaved = entryRepository.save(newEntryToSave);

    return entryRepository.findById(entrySaved.getId()).get();
  }

  private Image createImageIfExists(EntryInput entryInput) {
    if (entryInput.getImageData() == null || entryInput.getImageData().length <= 0) {
      return null;
    }

    return new Image(entryInput.getImageContentType(), entryInput.getImageData());
  }

  private Set<Tag> getOrCreateTags(EntryInput entryInput) {
    Set<String> tagInput =
        entryInput.getTags() != null
            ? entryInput.getTags().stream()
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .collect(Collectors.toSet())
            : Collections.emptySet();
    Set<Tag> tags = tagRepository.findByNameIn(tagInput);
    if (tags.size() >= tagInput.size()) {
      return tags;
    }

    Set<String> tagsInDb = tags.stream().map(Tag::getName).collect(Collectors.toSet());
    Set<Tag> tagsNotInDb =
        entryInput.getTags().stream()
            .filter(t -> !tagsInDb.contains(t))
            .map(Tag::new)
            .collect(Collectors.toSet());
    return Stream.of(tags, tagsNotInDb).flatMap(Collection::stream).collect(Collectors.toSet());
  }
}
