package com.cqrs.blogpost.service;

import com.cqrs.blogpost.domain.User;
import com.cqrs.blogpost.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final  UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init(){
        User user = new User();
        user.setUserName("Fsen");
        user.setEmail("sennfethi@gmail.com");
        user.setFirstName("Fethi");
        user.setLastName("ŞEN");

        userRepository.save(user);
    }
}
