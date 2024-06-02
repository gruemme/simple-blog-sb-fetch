package com.example.mySimpleBlog.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration test, only works with a running and configured database.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EntryControllerTest {

    private static final String SIMPLE_ENTRY_REQUEST_BODY = """
            {
                "title":"Hello",
                "text": "This is my blog"
            }
            """;

    private static final String ENTRY_REQUEST_BODY_WITH_TAGS = """
            {
                "title":"Hello",
                "text": "This is my blog",
                "tags": ["hello","world"]
            }
            """;

    private static final String ENTRY_REQUEST_BODY_WITH_IMAGE = """
            {
                "title":"My first picture in my blog",
                "text": "This is my blog",
                "imageContentType": "application/png",
                "imageData": "iVBORw0KGgoAAAANSUhEUgAAAMgAAADIAQMAAACXljzdAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAABlBMVEUAAP7////DYP5JAAAAAWJLR0QB/wIt3gAAAAlwSFlzAAALEgAACxIB0t1+/AAAAAd0SU1FB+QIGBcKN7/nP/UAAAAcSURBVFjD7cGBAAAAAMOg+VNf4QBVAQAAAAB8BhRQAAEAnyMVAAAAGXRFWHRjb21tZW50AENyZWF0ZWQgd2l0aCBHSU1Q569AywAAACV0RVh0ZGF0ZTpjcmVhdGUAMjAyMC0wOC0yNFQyMzoxMDo1NSswMzowMJB3XrkAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMjAtMDgtMjRUMjM6MTA6NTUrMDM6MDDhKuYFAAAAAElFTkSuQmCC"
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "alice", password = "alice")
    public void postSimpleEntryAndCorrectContent() throws Exception {

        this.mockMvc.perform(post("/entries")
                        .content(SIMPLE_ENTRY_REQUEST_BODY)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("This is my blog")));
    }

    @Test
    @WithMockUser(username = "bob", password = "bob")
    public void postEntryWithTagsAndExpectTwoCorrectTagsSuccess() throws Exception {
        this.mockMvc.perform(post("/entries")
                        .content(ENTRY_REQUEST_BODY_WITH_TAGS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.tags[*].name", containsInAnyOrder("hello", "world")));
    }

    @Test
    @WithMockUser(username = "alice", password = "alice")
    public void postEntryWithImageAndExpectImageAndTypeIsCorrect() throws Exception {
        this.mockMvc.perform(post("/entries")
                        .content(ENTRY_REQUEST_BODY_WITH_IMAGE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titleImage.imageContent", hasLength(468)))
                .andExpect(jsonPath("$.titleImage.contentType", is("application/png")));
    }
}