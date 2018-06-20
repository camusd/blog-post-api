package com.example.blogpostapi.service;

import com.example.blogpostapi.model.Post;

import java.util.List;

public interface PostService {

    List<Post> readPosts();
}
