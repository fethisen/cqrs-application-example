package com.cqrs.blogPostCqrsWrite.repository;

import com.cqrs.blogPostCqrsWrite.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
