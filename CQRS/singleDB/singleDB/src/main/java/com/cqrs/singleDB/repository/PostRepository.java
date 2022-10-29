package com.cqrs.singleDB.repository;

import com.cqrs.singleDB.domain.Post;
import com.cqrs.singleDB.service.dto.PostQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select new com.cqrs.singleDB.service.dto.PostQueryDto(p.title,p.content,u.userName)" +
            " from Post p " +
            " inner join User u on p.author.id = u.id")
    Page<PostQueryDto> findAllPost(Pageable pageable);
}
