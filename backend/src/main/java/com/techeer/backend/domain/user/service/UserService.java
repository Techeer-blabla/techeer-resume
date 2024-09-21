package com.techeer.backend.domain.user.service;

import com.techeer.backend.domain.user.entity.User;
import com.techeer.backend.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    // 다시 정해됨
    public User findUserByName(String userName) {
        return userRepository.findByName(userName);
    }

    public UserService(){

    }
}
