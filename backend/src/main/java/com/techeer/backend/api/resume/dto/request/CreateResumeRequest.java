package com.techeer.backend.api.resume.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

//todo Getter 한번만 사용하기
@Getter
public class CreateResumeRequest {
    // 나중에 로그인 생기면 수정해야 된다.
    @NotBlank(message = "username은 필수입니다.")
    private String username;

    @NotNull(message = "position는 필수입니다.")
    private String position;

    @NotNull(message = "career는 필수입니다.")
    private int career;

    private List<String> companyNames;

    private List<String> techStackNames;

}

