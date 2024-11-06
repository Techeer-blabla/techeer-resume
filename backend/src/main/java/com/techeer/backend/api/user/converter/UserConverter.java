package com.techeer.backend.api.user.converter;

import com.techeer.backend.api.user.domain.Role;
import com.techeer.backend.api.user.domain.SocialType;
import com.techeer.backend.api.user.domain.User;

public class UserConverter {
    public static User fromSignUp(String email, String refreshToken, Role role, SocialType socialType) {
        return User.builder()
                .email(email)
                .refreshToken(refreshToken)
                .role(role)
                .socialType(socialType)
                .build();
    }
}
