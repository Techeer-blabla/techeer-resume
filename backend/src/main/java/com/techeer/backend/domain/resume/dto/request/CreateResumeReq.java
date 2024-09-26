package com.techeer.backend.domain.resume.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.backend.domain.resume.entity.Resume;
import com.techeer.backend.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

//todo Getter 한번만 사용하기
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateResumeReq {
    // 나중에 로그인 생기면 수정해야 된다.

    @NotBlank
    private String username;

    private List<String> position;

    @NotNull
    private int career;

    private List<String> applyingCompany;

    private List<String> techStack;

    // todo toEntity로 변경 함수
    public Resume toEntity(User user, CreateResumeReq req) {
        return Resume.from(user, req);
    }

}

