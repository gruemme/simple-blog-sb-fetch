package com.example.mySimpleBlog.controller;

import com.example.mySimpleBlog.model.BlogUser;
import com.example.mySimpleBlog.model.Entry;
import com.example.mySimpleBlog.repositories.BlogUserRepository;
import com.example.mySimpleBlog.service.EntryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class BlogUserController {

    private final BlogUserRepository blogUserRepository;
    private final EntryService entryService;

    public BlogUserController(BlogUserRepository blogUserRepository, EntryService entryService) {
        this.blogUserRepository = blogUserRepository;
        this.entryService = entryService;
    }

    @GetMapping(path = "/users/me")
    @SecurityRequirement(name = "BasicAuth")
    public BlogUser getCurrentUser(Authentication authentication) {
        User currentUser = (User) (authentication.getPrincipal());
        Optional<BlogUser> author = blogUserRepository.findByUsername(currentUser.getUsername());

        return author.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }

    @GetMapping(path = "/users/{username}/entries")
    @Transactional
    public List<Entry> getEntriesByUser(@PathVariable String username, @ParameterObject Pageable pageable) {
        Optional<BlogUser> blogUserOptional = blogUserRepository.findByUsername(username);
        BlogUser author = blogUserOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return entryService.getEntriesByAuthor(author, pageable);
    }
}
