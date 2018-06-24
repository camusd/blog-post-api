package camusd.blogpostapi.assembly;

import camusd.blogpostapi.dto.CreatePostDTO;
import camusd.blogpostapi.model.Post;
import camusd.blogpostapi.dto.PostDTO;
import org.springframework.stereotype.Component;

@Component
public class PostAssembler {

    public PostDTO toDTO(Post post) {
        return PostDTO.newBuilder()
                .withPostId(post.getPostId())
                .withTitle(post.getTitle())
                .withBody(post.getBody())
                .build();
    }

    public Post toDomain(CreatePostDTO createPostDTO) {
        return Post.newBuilder()
                .withTitle(createPostDTO.getTitle())
                .withBody(createPostDTO.getBody())
                .build();
    }
}
