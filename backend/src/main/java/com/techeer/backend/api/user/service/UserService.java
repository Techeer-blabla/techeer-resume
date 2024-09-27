//package com.techeer.backend.domain.user.service;
//
//import com.techeer.backend.domain.user.entity.User;
//import com.techeer.backend.domain.user.repository.UserRepository;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    // 다시 정해됨
//    public Optional<User> findUserByName(String userName) {
//        return userRepository.findByUsername(userName);
//    }
//
//}
