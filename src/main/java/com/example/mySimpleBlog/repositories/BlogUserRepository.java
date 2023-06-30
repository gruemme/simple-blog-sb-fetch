package com.example.mySimpleBlog.repositories;

import com.example.mySimpleBlog.model.BlogUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {

    Optional<BlogUser> findByUsername(String username);
}
