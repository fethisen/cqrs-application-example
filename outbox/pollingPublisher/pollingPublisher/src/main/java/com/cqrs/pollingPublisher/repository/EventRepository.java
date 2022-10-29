package com.cqrs.pollingPublisher.repository;

import com.cqrs.pollingPublisher.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatus(Event.Status status);

    @Modifying
    @Query("update Event e set e.status= :status where e.id in :eventIds")
    void markAsPublished(@Param("eventIds") List<UUID> eventIds, @Param("status") Event.Status status);

}
