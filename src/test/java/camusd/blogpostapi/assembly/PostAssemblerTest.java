package camusd.blogpostapi.assembly;

import camusd.blogpostapi.dto.CreatePostDTO;
import camusd.blogpostapi.model.Post;
import camusd.blogpostapi.dto.PostDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PostAssemblerTest {

    @InjectMocks
    private PostAssembler subject;

    @Test
    public void toDTO() {
        Post post = Post.newBuilder()
                .withTitle("apple")
                .withBody("banana")
                .build();
        post.setPostId(1);
        PostDTO result = subject.toDTO(post);
        assertThat("The postId of the result does not match the input",
                result.getPostId(), is (post.getPostId()));
        assertThat("The title of the result does not match the input",
                result.getTitle(), is(post.getTitle()));
        assertThat("The body of the result does not match the input",
                result.getBody(), is(post.getBody()));
    }

    @Test
    public void toDomain() {
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setTitle("apple");
        createPostDTO.setBody("banana");
        Post result = subject.toDomain(createPostDTO);
        assertThat("The title of the result does not match the input",
                result.getTitle(), is(createPostDTO.getTitle()));
        assertThat("The body of the result does not match the input",
                result.getBody(), is(createPostDTO.getBody()));
    }
}