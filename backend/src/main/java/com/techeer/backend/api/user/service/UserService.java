package com.techeer.backend.api.user.service;


import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.dto.request.UserRegisterRequest;
import com.techeer.backend.api.user.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    // 다시 정해됨
    public Optional<User> findUserByName(String userName) {
        return userRepository.findByUsername(userName);
    }

    public boolean userExists(@NotBlank String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void register(UserRegisterRequest req) {
        // 비밀번호 암호화
        String encodePW = (passwordEncoder.encode(req.getPassword()));
        User user = User.from(req, encodePW);
        userRepository.save(user);
    }
}
