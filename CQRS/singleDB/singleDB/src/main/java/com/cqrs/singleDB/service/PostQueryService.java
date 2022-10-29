package com.cqrs.singleDB.service;

import com.cqrs.singleDB.repository.PostRepository;
import com.cqrs.singleDB.service.dto.PostQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostQueryService {
    private final PostRepository postRepository;

    public PostQueryService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @Transactional(readOnly = true)
    public Page<PostQueryDto> findAll(Pageable pageable) {
        return postRepository.findAllPost(pageable);
    }
}
