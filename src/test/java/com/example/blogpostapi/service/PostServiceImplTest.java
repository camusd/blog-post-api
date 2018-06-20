package com.example.blogpostapi.service;

import com.example.blogpostapi.model.post.Post;
import com.example.blogpostapi.model.post.CreatePostDTO;
import com.example.blogpostapi.repository.PostDAO;
import com.example.blogpostapi.service.Post.PostServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

    @Mock
    private PostDAO postDAO;

    @InjectMocks
    private PostServiceImpl subject;

    @Test
    public void readPosts_TwoEntitiesExist_ReturnsTwoEntities() {
        List<Post> expectedPosts = new ArrayList<>();
        Post post1 = new Post("apple", "banana");
        Post post2 = new Post("peach", "pear");
        expectedPosts.add(post1);
        expectedPosts.add(post2);
        when(postDAO.findAll()).thenReturn(expectedPosts);
        List<Post> result = subject.readPosts();
        assertThat("Unexpected number of posts returned", result.size(), is(2));
        assertEquals("The contents returned was not equal to the data from the repository",
                result, expectedPosts);
    }

    @Test
    public void readPosts_ZeroEntitiesExist_ReturnsEmptyList() {
        when(postDAO.findAll()).thenReturn(Collections.emptyList());
        List<Post> result = subject.readPosts();
        assertThat("Unexpected number of posts returned", result.size(), is(0));
    }

    @Test
    public void createPost_HappyPath_savesPost() {
        CreatePostDTO request = new CreatePostDTO();
        request.setTitle("apple");
        request.setBody("banana");
        when(postDAO.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Post result = subject.createPost(request);
        verify(postDAO, times(1)).save(result);
        assertThat("The title of the result does not match the input",
                result.getTitle(), is(request.getTitle()));
        assertThat("The body of the result does not match the input",
                result.getBody(), is(request.getBody()));
    }

    @Test(expected = BlogPostAPIServiceException.class)
    public void createPost_InputIsNull_ThrowsException() {
        subject.createPost(null);
    }
}