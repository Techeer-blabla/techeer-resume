package com.techeer.backend.api.user.converter;

import com.techeer.backend.api.user.domain.Role;
import com.techeer.backend.api.user.domain.User;

public class UserConverter {
    public static User fromSignUp(String email, String refreshToken, Role role){
        return User.builder()
                .email(email)
                .refreshToken(refreshToken)
                .role(role)
                .build();
    }
}
