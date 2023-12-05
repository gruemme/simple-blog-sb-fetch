package com.example.mySimpleBlog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {

  private static final String[] PATHS_WITH_FREE_ACCESS = {
    "/entry/**",
    "/entries/**",
    "/tag/**",
    "/tags/**",
    "/user/**",
    "/error",
    "/swagger-ui.html",
    "/swagger-ui/**",
    "/v3/**",
    "/",
    "/js/**",
    "/css/**",
    "/index.html",
    "/create.html",
    "/favicon.ico",
    "/tag.html*",
    "/login.html*"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorize -> {
              authorize
                  .requestMatchers("/me")
                  .authenticated()
                  .requestMatchers(HttpMethod.POST, "/entry")
                  .authenticated()
                  .requestMatchers(PATHS_WITH_FREE_ACCESS)
                  .permitAll()
                  .anyRequest()
                  .authenticated();
            })
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(
            sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
}
