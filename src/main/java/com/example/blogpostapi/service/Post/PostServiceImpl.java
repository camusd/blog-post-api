package com.example.blogpostapi.service.Post;

import com.example.blogpostapi.model.post.CreatePostDTO;
import com.example.blogpostapi.model.post.Post;
import com.example.blogpostapi.repository.PostDAO;
import com.example.blogpostapi.service.BlogPostAPIServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
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

    @Override
    public Post createPost(CreatePostDTO createPostDTO) {
        if (createPostDTO == null) {
            throw BlogPostAPIServiceException.invalidArgument("CreatePostDTO cannot be null");
        }
        Post post = Post.newBuilder()
                .withTitle(createPostDTO.getTitle())
                .withBody(createPostDTO.getBody())
                .build();
        try {
            return postDAO.save(post);
        } catch (ConstraintViolationException e) {
            throw BlogPostAPIServiceException.invalidArgument(e.getConstraintViolations());
        }
    }
}
