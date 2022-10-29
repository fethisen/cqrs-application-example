package com.cqrs.blogPostCqrsWrite.eventstore;

import org.springframework.data.jpa.repository.JpaRepository;

interface EventRepository extends JpaRepository<EventDescriptor, Long> {}
