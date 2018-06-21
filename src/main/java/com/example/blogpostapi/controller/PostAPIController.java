package com.example.blogpostapi.controller;

import com.example.blogpostapi.assembly.PostAssembler;
import com.example.blogpostapi.dto.CreatePostDTO;
import com.example.blogpostapi.model.Post;
import com.example.blogpostapi.dto.PostDTO;
import com.example.blogpostapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PostAPIController {

    private PostService postService;

    private PostAssembler postAssembler;

    @Autowired
    public PostAPIController(PostService postService, PostAssembler postAssembler) {
        this.postService = postService;
        this.postAssembler = postAssembler;
    }

    @GetMapping("/posts")
    @ResponseBody
    public List<PostDTO> readPosts() {
        return postService.readPosts().stream()
                .map(post -> postAssembler.toDTO(post))
                .collect(Collectors.toList());
    }

    @PostMapping("/post")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public PostDTO createPost(@RequestBody CreatePostDTO createPostDTO) {
        Post post = postService.createPost(createPostDTO);
        return postAssembler.toDTO(post);
    }

}
