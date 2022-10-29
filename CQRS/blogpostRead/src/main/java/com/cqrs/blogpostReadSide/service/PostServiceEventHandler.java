package com.cqrs.blogpostReadSide.service;

import com.cqrs.blogpostReadSide.config.RabbitMQConfig;
import com.cqrs.blogpostReadSide.domain.Post;
import com.cqrs.blogpostReadSide.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceEventHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final PostRepository postRepository;
    private final RabbitMQConfig rabbitMQConfig;

    public PostServiceEventHandler(PostRepository postRepository, RabbitMQConfig rabbitMQConfig) {
        this.postRepository = postRepository;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    @RabbitListener(queues = "#{rabbitMQConfig.getQueueName()}")
    public void consume(String postStr) {
        try{
            Post post = OBJECT_MAPPER.readValue(postStr, Post.class);
            this.savePost(post);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void savePost(Post post) {
        this.postRepository.save(post);
    }
}
