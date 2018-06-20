package com.example.blogpostapi.service.Post;

import com.example.blogpostapi.model.post.CreatePostDTO;
import com.example.blogpostapi.model.post.Post;

import java.util.List;

public interface PostService {

    List<Post> readPosts();

    Post createPost(CreatePostDTO createPostDTO);
}
