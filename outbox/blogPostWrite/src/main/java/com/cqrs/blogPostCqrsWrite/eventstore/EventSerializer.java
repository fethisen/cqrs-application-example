package com.cqrs.blogPostCqrsWrite.eventstore;

import com.cqrs.blogPostCqrsWrite.domain.Post;
import com.cqrs.blogPostCqrsWrite.service.PostCreatedEvent;
import com.cqrs.blogPostCqrsWrite.service.vm.PostVM;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class EventSerializer {
    private final ObjectMapper objectMapper;

    public EventSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    String serialize(PostVM post) throws JsonProcessingException {
        return objectMapper.writeValueAsString(post);
    }
}
