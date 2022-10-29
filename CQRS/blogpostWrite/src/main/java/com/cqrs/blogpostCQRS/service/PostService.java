package com.cqrs.blogpostCQRS.service;

import com.cqrs.blogpostCQRS.config.RabbitMQConfig;
import com.cqrs.blogpostCQRS.domain.Post;
import com.cqrs.blogpostCQRS.domain.User;
import com.cqrs.blogpostCQRS.repository.PostRepository;
import com.cqrs.blogpostCQRS.service.dto.PostDTO;
import com.cqrs.blogpostCQRS.service.vm.PostVM;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final PostRepository postRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;
    private final UserService userService;

    public PostService(PostRepository postRepository, RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig, UserService userService) {
        this.postRepository = postRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
        this.userService = userService;
    }

    public Long save(PostDTO postDTO) throws Exception {
        Post post = toEntity(postDTO);
        post = postRepository.save(post);
        raiseEvent(createPostObject(post));
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

    /**
     * Instead of using Exception, the Business exception should be used
     * @param post
     * @return
     * @throws Exception
     */
    private PostVM createPostObject(Post post) throws Exception {
        User user  = userService.getUserByUserId(post.getAuthor().getId()).orElseThrow(
                () -> new Exception("User not found with userId " + post.getAuthor().getId()));

        PostVM postVM = new PostVM();
        postVM.setId(post.getId());
        postVM.setAuthorName(user.getUserName());
        postVM.setContent(post.getContent());
        postVM.setTitle(post.getTitle());
        return postVM;
    }
    private void raiseEvent(PostVM postVM){
        try{
            String value = OBJECT_MAPPER.writeValueAsString(postVM);
            rabbitTemplate.convertAndSend(rabbitMQConfig.getExchangeName(), rabbitMQConfig.getRoutingKey(), value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
