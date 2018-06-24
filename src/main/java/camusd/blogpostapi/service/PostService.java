package camusd.blogpostapi.service;

import camusd.blogpostapi.dto.CreatePostDTO;
import camusd.blogpostapi.model.Post;

import java.util.List;

public interface PostService {

    List<Post> readPosts();

    Post createPost(CreatePostDTO createPostDTO);
}
