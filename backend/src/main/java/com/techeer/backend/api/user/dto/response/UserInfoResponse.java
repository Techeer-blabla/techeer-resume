package com.techeer.backend.api.user.dto.response;

import com.techeer.backend.api.user.domain.Role;
import lombok.Builder;

@Builder
public class UserInfoResponse {
    private String username;
    private String email;
    private Role role;
}
