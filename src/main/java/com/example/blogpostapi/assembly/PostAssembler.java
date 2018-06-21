package com.example.blogpostapi.assembly;

import com.example.blogpostapi.dto.CreatePostDTO;
import com.example.blogpostapi.model.Post;
import com.example.blogpostapi.dto.PostDTO;
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
