package camusd.blogpostapi.service;

import camusd.blogpostapi.BlogPostApiException;
import camusd.blogpostapi.assembly.PostAssembler;
import camusd.blogpostapi.dto.CreatePostDTO;
import camusd.blogpostapi.model.Post;
import camusd.blogpostapi.repository.PostDAO;
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
