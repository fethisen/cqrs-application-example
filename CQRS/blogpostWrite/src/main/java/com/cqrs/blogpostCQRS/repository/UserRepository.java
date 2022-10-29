package com.cqrs.blogpostCQRS.repository;

import com.cqrs.blogpostCQRS.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
