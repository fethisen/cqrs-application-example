package com.cqrs.blogpost.service;

import com.cqrs.blogpost.controller.vm.PostResultVM;
import com.cqrs.blogpost.domain.Post;
import com.cqrs.blogpost.repository.PostRepository;
import com.cqrs.blogpost.service.dto.PostDTO;
import com.cqrs.blogpost.service.mapper.PostMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public PostDTO save(PostDTO postDTO) {
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }
    @Transactional(readOnly = true)
    public Page<PostResultVM> findAll(Pageable pageable) {
        return postRepository.findAllPost(pageable);
    }
}
