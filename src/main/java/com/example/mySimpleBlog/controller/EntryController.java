package com.example.mySimpleBlog.controller;

import com.example.mySimpleBlog.dto.EntryInput;
import com.example.mySimpleBlog.model.BlogUser;
import com.example.mySimpleBlog.model.Entry;
import com.example.mySimpleBlog.repositories.BlogUserRepository;
import com.example.mySimpleBlog.service.EntryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EntryController {

    private final EntryService entryService;
    private final BlogUserRepository blogUserRepository;

    public EntryController(EntryService entryService,
                           BlogUserRepository blogUserRepository) {
        this.entryService = entryService;
        this.blogUserRepository = blogUserRepository;
    }

    @GetMapping(path = "/entries", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Entry> getAllEntries(@ParameterObject Pageable pageable) {
        return entryService.getAllEntries(pageable);
    }

    @GetMapping(path = "/entry/{entryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Entry getEntryById(@PathVariable(name = "entryId", required = true) Long entryId) {
        return entryService.findById(entryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin
    @PostMapping(path = "/entry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "BasicAuth")
    @Transactional
    public Entry createEntry(Authentication authentication, @RequestBody EntryInput entryInput) {
        User currentUser = (User) (authentication.getPrincipal());
        BlogUser author = blogUserRepository
                .findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        return entryService.createEntryForUser(entryInput, author);
    }
}
