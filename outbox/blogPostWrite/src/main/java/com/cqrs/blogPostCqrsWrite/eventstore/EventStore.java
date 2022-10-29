package com.cqrs.blogPostCqrsWrite.eventstore;

import com.cqrs.blogPostCqrsWrite.service.PostCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class EventStore {
    private final EventRepository eventRepository;
    private final EventSerializer eventSerializer;

    public void save(PostCreatedEvent event) throws JsonProcessingException {
        eventRepository.save(
                new EventDescriptor(
                        event.getId(),
                        eventSerializer.serialize(event.getPost()),
                        event.getOccurredAt(),
                        event.getType()
                )
        );
    }
}
