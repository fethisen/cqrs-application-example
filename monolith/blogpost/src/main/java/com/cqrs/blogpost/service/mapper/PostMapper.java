package com.cqrs.blogpost.service.mapper;

import com.cqrs.blogpost.domain.Post;
import com.cqrs.blogpost.domain.User;
import com.cqrs.blogpost.service.dto.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "author", source = "authorId", qualifiedByName = "setUserIdToEntity")
    Post toEntity(PostDTO postDTO);

    @Mapping(target = "authorId", source = "author", qualifiedByName = "setUserIdToDto")
    PostDTO toDto(Post post);

    @Named("setUserIdToEntity")
    default User setUserIdToEntity(Long authorId) {
        User user = new User();
        user.setId(authorId);
        return user;
    }

    @Named("setUserIdToDto")
    default Long setUserIdToDto(User user) {
        return user.getId();
    }
}