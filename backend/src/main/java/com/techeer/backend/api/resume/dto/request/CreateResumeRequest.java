package com.techeer.backend.api.resume.dto.request;

import com.techeer.backend.api.tag.position.Position;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

//todo Getter 한번만 사용하기
@Getter
public class CreateResumeRequest {

    @NotNull(message = "position는 필수입니다.")
    private Position position;

    @NotNull(message = "career는 필수입니다.")
    private int career;

    private List<String> companyNames;

    private List<String> techStackNames;

}

