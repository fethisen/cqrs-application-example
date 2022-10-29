package com.cqrs.singleDB.service;

import com.cqrs.singleDB.domain.Post;
import com.cqrs.singleDB.repository.PostRepository;
import com.cqrs.singleDB.service.dto.PostCommandDto;
import com.cqrs.singleDB.service.mapper.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostCommandService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostCommandService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public Long save(PostCommandDto postCommandDto) {
        Post post = postMapper.toEntity(postCommandDto);
        return postRepository.save(post).getId();
    }
}
