package com.cqrs.blogPostCqrsWrite.eventstore;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "outbox")
public class EventDescriptor {
    @Id
    @Type(type = "uuid-char")
    private UUID id;
    @Column(length = 10000)
    private String content;

    private Date occurredAt;

    private String type;

    @Enumerated(value = EnumType.STRING)
    private Status status = Status.CREATED;

    enum Status {
        CREATED, PUBLISHED;
    }

    EventDescriptor(UUID id, String content, Date occurredAt, String type) {
        this.id = id;
        this.content = content;
        this.occurredAt = occurredAt;
        this.type = type;
    }

    public EventDescriptor() {
    }
}
