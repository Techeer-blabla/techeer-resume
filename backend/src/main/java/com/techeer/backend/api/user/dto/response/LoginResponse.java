package com.techeer.backend.api.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class LoginResponse {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;


}
