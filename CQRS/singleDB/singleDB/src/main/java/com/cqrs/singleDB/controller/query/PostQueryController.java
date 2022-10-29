package com.cqrs.singleDB.controller.query;

import com.cqrs.singleDB.service.PostQueryService;
import com.cqrs.singleDB.service.dto.PostQueryDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "false")
public class PostQueryController {
    private final PostQueryService postQueryService;

    public PostQueryController(PostQueryService postQueryService) {
        this.postQueryService = postQueryService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostQueryDto>> getAllPosts(Pageable pageable) {
        Page<PostQueryDto> page = postQueryService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }
}
