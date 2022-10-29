package com.cqrs.blogPostCqrsWrite.controller;

import com.cqrs.blogPostCqrsWrite.service.PostService;
import com.cqrs.blogPostCqrsWrite.service.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<Long> createPost(@RequestBody PostDTO postDTO) throws Exception {
        log.info("There is a new post to publish, post title :{}",postDTO.getTitle());
        Long postId = postService.save(postDTO);
        return ResponseEntity
                .created(new URI("/api/posts/" + postId))
                .body(postId);
    }
}
