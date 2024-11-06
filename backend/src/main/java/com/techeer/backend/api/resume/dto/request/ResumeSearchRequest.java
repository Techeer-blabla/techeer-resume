package com.techeer.backend.api.resume.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class ResumeSearchRequest {
    private List<String> positions; // 여러 포지션을 받을 수 있도록 변경
    private Integer minCareer; // 최소 경력
    private Integer maxCareer; // 최대 경력
    private List<String> techStackNames;
    private List<String> companyNames;
}
