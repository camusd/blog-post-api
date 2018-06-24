package camusd.blogpostapi.service;

import camusd.blogpostapi.BlogPostApiException;
import camusd.blogpostapi.assembly.PostAssembler;
import camusd.blogpostapi.model.Post;
import camusd.blogpostapi.dto.CreatePostDTO;
import camusd.blogpostapi.repository.PostDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.Validator;
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

    @Mock
    private PostAssembler postAssembler;

    @Mock
    private Validator validator;

    @InjectMocks
    private PostServiceImpl subject;

    @Test
    public void readPosts_TwoEntitiesExist_ReturnsTwoEntities() {
        List<Post> expectedPosts = new ArrayList<>();
        Post post1 = Post.newBuilder()
                .withTitle("apple")
                .withBody("banana")
                .build();
        Post post2 = Post.newBuilder()
                .withTitle("peach")
                .withBody("pear")
                .build();
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
    public void createPost_HappyPath_SavesPost() {
        CreatePostDTO request = new CreatePostDTO();
        request.setTitle("apple");
        request.setBody("banana");
        when(postDAO.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(postAssembler.toDomain(any(CreatePostDTO.class))).thenReturn(Post.newBuilder().build());
        when(validator.validate(any(Post.class))).thenReturn(Collections.emptySet());
        Post result = subject.createPost(request);
        verify(postDAO, times(1)).save(result);
    }

    @Test(expected = BlogPostApiException.class)
    public void createPost_InputIsNull_ThrowsException() {
        subject.createPost(null);
    }
}