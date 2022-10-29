package com.cqrs.blogpostCQRS.controller;

import com.cqrs.blogpostCQRS.service.PostService;
import com.cqrs.blogpostCQRS.service.dto.PostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> createPost(@Valid @RequestBody PostDTO postDTO) throws Exception {
        Long postId = postService.save(postDTO);
        return ResponseEntity
                .created(new URI("/api/posts/" + postId))
                .body(postId);
    }
}
