package com.techeer.backend.domain.resume.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.backend.domain.resume.entity.Resume;
import com.techeer.backend.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateResumeReq {
    // 나중에 로그인 생기면 수정해야 된다.
    @Getter
    @NotBlank
    private String username;

    @Getter
    private List<String> position;

    @Getter
    @NotNull
    private int career;

    @Getter
    private List<String> applyingCompany;

    @Getter
    private List<String> techStack;

    public Resume toResume(User user, CreateResumeReq req) {
        return Resume.createResume(user, req);
    }

}

