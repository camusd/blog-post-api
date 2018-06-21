package com.example.blogpostapi.controller.post;

import com.example.blogpostapi.BlogPostApiApplication;
import com.example.blogpostapi.H2TestProfileJPAConfig;
import com.example.blogpostapi.dto.CreatePostDTO;
import com.example.blogpostapi.model.Post;
import com.example.blogpostapi.dto.PostDTO;
import com.example.blogpostapi.repository.PostDAO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        BlogPostApiApplication.class,
        H2TestProfileJPAConfig.class
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostAPIControllerIntegrationTest {

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper = new ObjectMapper();

    @After
    public void tearDown() {
        postDAO.deleteAll();
    }

    @Test
    public void readPosts_ValidInput_ConvertsToPostDTOList() throws Exception {
        Post post = Post.newBuilder()
                .withTitle("apple")
                .withBody("banana")
                .build();
        postDAO.save(post);
        String response = mvc.perform(get("/posts")).andDo(print()).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<PostDTO> postDTOs = mapper.readValue(response, new TypeReference<List<PostDTO>>(){});
        assertThat("Unexpected number of postDTOs returned", postDTOs.size(), is(1));
    }

    @Test
    public void createPost_ValidInput_ConvertsToPostDTO() throws Exception {
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setTitle("apple");
        createPostDTO.setBody("banana");
        String response = mvc.perform(post("/post").content(mapper.writeValueAsString(createPostDTO))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        PostDTO postDTO = mapper.readValue(response, PostDTO.class);
        assertNotNull("Result was null", postDTO);
    }

    @Test
    public void createPost_NullInput_Returns400() throws Exception {
        CreatePostDTO createPostDTO = new CreatePostDTO();
        String response = mvc.perform(post("/post").content(mapper.writeValueAsString(createPostDTO))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();
    }
}