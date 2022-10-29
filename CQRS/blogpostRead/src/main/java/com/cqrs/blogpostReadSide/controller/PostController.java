package com.cqrs.blogpostReadSide.controller;

import com.cqrs.blogpostReadSide.domain.Post;
import com.cqrs.blogpostReadSide.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(Pageable pageable) {
        Page<Post> page = postService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }
}
