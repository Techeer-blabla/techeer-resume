package com.techeer.backend.api.user.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.backend.api.user.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignUpRequest {
    @NotBlank(message = "이름 입력하세요")
    private String username;

    @NotNull(message = "권한을 입력하세요 (REGULAR, TECHEER, ADMIN)")
    private Role role;
}

