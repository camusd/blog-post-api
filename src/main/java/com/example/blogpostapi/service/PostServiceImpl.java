package com.example.blogpostapi.service;

import com.example.blogpostapi.assembly.PostAssembler;
import com.example.blogpostapi.dto.CreatePostDTO;
import com.example.blogpostapi.model.Post;
import com.example.blogpostapi.repository.PostDAO;
import com.example.blogpostapi.BlogPostApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private PostDAO postDAO;

    private PostAssembler postAssembler;

    private Validator validator;

    @Autowired
    public PostServiceImpl(PostDAO postDAO, PostAssembler postAssembler, Validator validator) {
        this.postDAO = postDAO;
        this.postAssembler = postAssembler;
        this.validator = validator;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> readPosts() {
        return postDAO.findAll();
    }

    @Override
    @Transactional
    public Post createPost(CreatePostDTO createPostDTO) {
        if (createPostDTO == null) {
            throw BlogPostApiException.invalidArgument("CreatePostDTO cannot be null");
        }
        Post post = postAssembler.toDomain(createPostDTO);
        validate(post);
        return postDAO.save(post);
    }

    private void validate(Post post) {
        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        if (!violations.isEmpty()) {
            throw BlogPostApiException.invalidArgument(violations);
        }
    }
}
