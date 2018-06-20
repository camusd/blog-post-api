package com.example.blogpostapi.service;

import com.example.blogpostapi.model.Post;
import com.example.blogpostapi.repository.PostDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    @Mock
    private PostDAO postDAO;

    @InjectMocks
    private PostServiceImpl subject;

    @Test
    public void readPosts_HappyPath_ReturnsTwoEntities() {
        List<Post> expectedPosts = new ArrayList<>();
        Post post1 = new Post();
        Post post2 = new Post();
        expectedPosts.add(post1);
        expectedPosts.add(post2);
        when(postDAO.findAll()).thenReturn(expectedPosts);
        List<Post> result = subject.readPosts();
        assertThat("readPosts returned unexpected number of posts", result.size(), is(2));
        assertEquals("The contents returned from readPosts was not equal to the data from the repository",
                result, expectedPosts);
    }

    @Test
    public void readPosts_HappyPath_ReturnsEmptyList() {
        when(postDAO.findAll()).thenReturn(Collections.emptyList());
        List<Post> result = subject.readPosts();
        assertThat("readPosts returned unexpected number of posts", result.size(), is(0));
    }
}