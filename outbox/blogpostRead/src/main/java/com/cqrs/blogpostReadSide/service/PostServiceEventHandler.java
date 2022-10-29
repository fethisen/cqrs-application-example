package com.cqrs.blogpostReadSide.service;

import com.cqrs.blogpostReadSide.config.RabbitMQConfig;
import com.cqrs.blogpostReadSide.domain.Post;
import com.cqrs.blogpostReadSide.repository.PostRepository;
import com.cqrs.blogpostReadSide.service.dto.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceEventHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final PostRepository postRepository;
    private final RabbitMQConfig rabbitMQConfig;
    private final RabbitTemplate rabbitTemplate;


    @RabbitListener(queues = "#{rabbitMQConfig.getQueueNameListener()}")
    public void consume(String eventStr) {
        try{
            Event event = OBJECT_MAPPER.readValue(eventStr, Event.class);
            log.info("Object was taken to message broker, event id: {}", event.getId());
            Post post = OBJECT_MAPPER.readValue(event.getContent(), Post.class);
            this.savePost(post);
            rabbitTemplate.convertAndSend(rabbitMQConfig.getExchangeName(), rabbitMQConfig.getRoutingKeyPublisher(), eventStr);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void savePost(Post post) {
        this.postRepository.save(post);
    }
}
