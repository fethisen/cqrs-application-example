package com.cqrs.blogpostReadSide.repository;

import com.cqrs.blogpostReadSide.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {}