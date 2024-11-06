package com.techeer.backend.api.user.dto.response;

import com.techeer.backend.api.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoResponse {

    private final String username;
    private final String email;
    private final Role role;

    @Builder
    public UserInfoResponse(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
