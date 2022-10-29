package com.cqrs.singleDB.service.mapper;

import com.cqrs.singleDB.domain.Post;
import com.cqrs.singleDB.domain.User;
import com.cqrs.singleDB.service.dto.PostCommandDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostCommandDto, Post> {
    @Mapping(target = "author", source = "authorId", qualifiedByName = "setUserIdToEntity")
    Post toEntity(PostCommandDto postCommandDto);

    @Mapping(target = "authorId", source = "author", qualifiedByName = "setUserIdToDto")
    PostCommandDto toDto(Post post);

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
