package com.techeer.backend.api.user.dto.response;

import com.techeer.backend.api.aifeedback.domain.AIFeedback;
import com.techeer.backend.api.aifeedback.dto.AIFeedbackResponse;
import com.techeer.backend.api.user.domain.Role;
import com.techeer.backend.api.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoResponse {

    private final String username;
    private final String email;
    private final Role role;

    @Builder
    public UserInfoResponse( String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // 정적 팩토리 메서드: AIFeedback 엔티티에서 데이터를 받아 DTO로 변환
    public static com.techeer.backend.api.user.dto.response.UserInfoResponse of(User user) {
        return UserInfoResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
