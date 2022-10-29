package com.cqrs.blogPostCqrsWrite.repository;

import com.cqrs.blogPostCqrsWrite.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {}
