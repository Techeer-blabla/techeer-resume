package com.techeer.backend.domain.resume.dto.request;

import com.techeer.backend.domain.resume.entity.Position;
import lombok.Getter;

import java.util.List;

@Getter
public class ResumeSearchRequest {
    private List<Position> positions; // 여러 포지션을 받을 수 있도록 변경
    private Integer minCareer; // 최소 경력
    private Integer maxCareer; // 최대 경력private Position position;
    private List<String> techStacks;
}
