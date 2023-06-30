package com.example.mySimpleBlog.service;

import com.example.mySimpleBlog.model.BlogUser;
import com.example.mySimpleBlog.repositories.BlogUserRepository;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final BlogUserRepository blogUserRepository;

    public UserDetailsServiceImpl(BlogUserRepository blogUserRepository) {
        this.blogUserRepository = blogUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Try to find " + username);
        Optional<BlogUser> blogUserOptional = blogUserRepository.findByUsername(username);
        if (blogUserOptional.isEmpty()) {
            System.out.println("not found " + username);
            throw new UsernameNotFoundException(username);
        }
        BlogUser user = blogUserOptional.get();
        System.out.println("found " + user);

        return new User(user.getUsername(), user.getPasswordHash(), Set.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
