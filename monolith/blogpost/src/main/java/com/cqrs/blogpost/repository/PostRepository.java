package com.cqrs.blogpost.repository;

import com.cqrs.blogpost.controller.vm.PostResultVM;
import com.cqrs.blogpost.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select new com.cqrs.blogpost.controller.vm.PostResultVM(p.title,p.content,u.userName)" +
            " from Post p " +
            " inner join User u on p.author.id = u.id")
    Page<PostResultVM> findAllPost(Pageable pageable);
}
