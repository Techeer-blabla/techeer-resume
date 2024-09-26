package com.techeer.backend.domain.resume.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.backend.domain.resume.entity.Position;
import com.techeer.backend.domain.resume.entity.Resume;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

//todo Getter 한번만 사용하기
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateResumeRequest {
    // 나중에 로그인 생기면 수정해야 된다.

    @NotBlank
    private String username;

    @NotNull
    private Position position;

    @NotNull()
    private int career;

    private List<String> applyingCompany;

    private List<String> techStack;

    public Resume toEntity() {
        return new Resume(this);
    }

}

