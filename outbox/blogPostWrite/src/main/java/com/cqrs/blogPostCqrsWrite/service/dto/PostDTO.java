package com.cqrs.blogPostCqrsWrite.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PostDTO implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
}
