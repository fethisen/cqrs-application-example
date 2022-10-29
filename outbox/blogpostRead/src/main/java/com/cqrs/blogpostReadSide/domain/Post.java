package com.cqrs.blogpostReadSide.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document
public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private String authorName;
    private Date publishedAt;
}
