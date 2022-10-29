package com.cqrs.blogpost.controller;

import com.cqrs.blogpost.controller.vm.PostResultVM;
import com.cqrs.blogpost.service.PostService;
import com.cqrs.blogpost.service.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) throws URISyntaxException {
        PostDTO result = postService.save(postDTO);
        return ResponseEntity
                .created(new URI("/api/posts/" + result.getId()))
                .body(result);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResultVM>> getAllPosts(Pageable pageable) {
        Page<PostResultVM> page = postService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }
}
