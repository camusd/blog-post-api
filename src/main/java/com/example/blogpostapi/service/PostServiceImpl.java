package com.example.blogpostapi.service;

import com.example.blogpostapi.model.Post;
import com.example.blogpostapi.repository.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostDAO postDAO;

    @Autowired
    public PostServiceImpl(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    public List<Post> readPosts() {
        return postDAO.findAll();
    }
}
