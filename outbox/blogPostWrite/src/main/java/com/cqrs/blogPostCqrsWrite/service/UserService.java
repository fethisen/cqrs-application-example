package com.cqrs.blogPostCqrsWrite.service;

import com.cqrs.blogPostCqrsWrite.domain.User;
import com.cqrs.blogPostCqrsWrite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUserName("Fsen");
        user.setEmail("sennfethi@gmail.com");
        user.setFirstName("Fethi");
        user.setLastName("ŞEN");

        userRepository.save(user);
    }

    public Optional<User> getUserByUserId(Long id) {
        return userRepository.findById(id);
    }
}
