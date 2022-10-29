package com.cqrs.pollingPublisher.service;

import com.cqrs.pollingPublisher.config.RabbitMQConfig;
import com.cqrs.pollingPublisher.domain.Event;
import com.cqrs.pollingPublisher.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventHandlerService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final RabbitMQConfig rabbitMQConfig;
    private final EventRepository eventRepository;


    @RabbitListener(queues = "#{rabbitMQConfig.getQueueNameListener()}")
    public void consume(String eventStr) {
        try{
            Event event = OBJECT_MAPPER.readValue(eventStr, Event.class);
            log.info("Event will be delete, even id :{}", event.getId());
            eventRepository.delete(event);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
