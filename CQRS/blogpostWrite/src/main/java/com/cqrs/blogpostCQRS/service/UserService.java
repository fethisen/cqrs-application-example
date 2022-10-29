package com.cqrs.blogpostCQRS.service;

import com.cqrs.blogpostCQRS.domain.User;
import com.cqrs.blogpostCQRS.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

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
    public Optional<User> getUserByUserId(Long id){
        return userRepository.findById(id);
    }
}
