package com.cqrs.blogpostReadSide.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Event implements Serializable {
    private UUID id;
    private String content;
    private Date occurredAt;
    private String type;
    private String status;

}
