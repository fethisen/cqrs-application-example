package com.cqrs.blogPostCqrsWrite.service;

import com.cqrs.blogPostCqrsWrite.domain.Post;
import com.cqrs.blogPostCqrsWrite.service.vm.PostVM;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class PostCreatedEvent{
    public static final String TYPE = "post-created";
    UUID id = UUID.randomUUID();
    Date occurredAt = new Date();
    String type = TYPE;
    PostVM post;
}