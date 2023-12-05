package com.example.mySimpleBlog;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.mySimpleBlog.controller.EntryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MySimpleBlogApplicationTests {

  @Autowired private EntryController entryController;

  @Test
  void contextLoads() {
    assertThat(entryController).isNotNull();
  }
}
