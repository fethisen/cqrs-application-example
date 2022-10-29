package com.cqrs.singleDB.controller.command;

import com.cqrs.singleDB.service.PostCommandService;
import com.cqrs.singleDB.service.PostQueryService;
import com.cqrs.singleDB.service.dto.PostCommandDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "true")
public class PostCommandController {
    private final PostCommandService postCommandService;

    public PostCommandController(PostCommandService postCommandService) {
        this.postCommandService = postCommandService;
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> createPost(@RequestBody PostCommandDto postCommandDto) throws URISyntaxException {
        Long postId = postCommandService.save(postCommandDto);
        return ResponseEntity
                .created(new URI("/api/posts/" + postId))
                .body(postId);
    }
}
