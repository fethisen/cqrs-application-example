package com.cqrs.blogPostCqrsWrite.service;

import com.cqrs.blogPostCqrsWrite.domain.Post;
import com.cqrs.blogPostCqrsWrite.domain.User;
import com.cqrs.blogPostCqrsWrite.eventstore.EventStore;
import com.cqrs.blogPostCqrsWrite.repository.PostRepository;
import com.cqrs.blogPostCqrsWrite.service.dto.PostDTO;
import com.cqrs.blogPostCqrsWrite.service.vm.PostVM;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final PostRepository postRepository;
    private final UserService userService;
    private final EventStore eventStore;


    public Long save(PostDTO postDTO) throws Exception {
        Post post = toEntity(postDTO);
        post = postRepository.save(post);
        eventStore.save(createPostCreatedEvent(post));
        return post.getId();
    }

    private Post toEntity(PostDTO postDTO) {
        User user = new User();
        user.setId(postDTO.getAuthorId());
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setAuthor(user);
        return post;
    }

    private PostCreatedEvent createPostCreatedEvent(Post post) throws Exception {
        User user  = userService.getUserByUserId(post.getAuthor().getId()).orElseThrow(
                () -> new Exception("User not found with userId " + post.getAuthor().getId()));

        PostVM postVM = new PostVM();
        postVM.setId(post.getId());
        postVM.setAuthorName(user.getUserName());
        postVM.setContent(post.getContent());
        postVM.setTitle(post.getTitle());
        postVM.setPublishedAt(post.getPublishedAt());

        PostCreatedEvent event = new PostCreatedEvent();
        event.setPost(postVM);
        return event;
    }

}
