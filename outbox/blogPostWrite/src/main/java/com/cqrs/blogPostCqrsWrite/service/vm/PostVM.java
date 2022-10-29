package com.cqrs.blogPostCqrsWrite.service.vm;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostVM {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private Date publishedAt;
}
