package com.example.mySimpleBlog.repositories;

import com.example.mySimpleBlog.model.BlogUser;
import com.example.mySimpleBlog.model.Entry;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    List<Entry> findByTags_NameOrderByCreatedDesc(String tagName);

    List<Entry> findByAuthor(BlogUser author, Pageable pageable);
}
