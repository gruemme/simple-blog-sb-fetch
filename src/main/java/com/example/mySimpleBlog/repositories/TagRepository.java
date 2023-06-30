package com.example.mySimpleBlog.repositories;

import com.example.mySimpleBlog.dto.TagCount;
import com.example.mySimpleBlog.model.Tag;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Set<Tag> findByNameIn(Collection<String> names);

    @Query("SELECT new com.example.mySimpleBlog.dto.TagCount(t.name, COUNT(e)) FROM Tag t LEFT JOIN t.entries e GROUP BY t ORDER BY COUNT(e) DESC")
    List<TagCount> findTagCount();
}
