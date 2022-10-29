package com.cqrs.pollingPublisher.service;

import com.cqrs.pollingPublisher.config.RabbitMQConfig;
import com.cqrs.pollingPublisher.domain.Event;
import com.cqrs.pollingPublisher.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EventPublisherService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final EventRepository eventRepository;
    private final RabbitTemplate rabbitTemplate;

    private final RabbitMQConfig rabbitMQConfig;

    @PostConstruct
    public void init() {
        List<Event> eventList = eventRepository.findByStatus(Event.Status.PUBLISHED);
        eventList.forEach(event -> {event.setStatus(Event.Status.CREATED);eventRepository.save(event);});
    }

    @Scheduled(fixedRate = 3000)
    public void publishAllPeriodically() {
        List<Event> eventList = toPublish();
        eventList.forEach(this::raiseEvent);
        markAsPublished(eventList, Event.Status.PUBLISHED);

    }

    private List<Event> toPublish() {
        return eventRepository.findByStatus(Event.Status.CREATED);
    }

    private void raiseEvent(Event event) {
        try {
            String value = OBJECT_MAPPER.writeValueAsString(event);
            rabbitTemplate.convertAndSend(rabbitMQConfig.getExchangeName(), rabbitMQConfig.getRoutingKeyPublisher(), value);
            log.info("Object was sent to message broker, event id :{}", event.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void markAsPublished(List<Event> events, Event.Status status) {
        if (CollectionUtils.isEmpty(events)) return;
        eventRepository.markAsPublished(
                events.stream()
                        .map(Event::getId)
                        .collect(Collectors.toList())
                , status
        );
    }
}
