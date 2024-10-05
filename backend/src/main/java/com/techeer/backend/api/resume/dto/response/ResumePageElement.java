package com.techeer.backend.api.resume.dto.response;

import com.techeer.backend.api.resume.domain.Position;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.domain.ResumeTechStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ResumePageElement {
    private Long resumeId;
    private String userName;
    private String resumeName;
    private Position position;
    private int career;
    private List<ResumeTechStack> techStack;

    private ResumePageElement(Long id, String username, String resumeName, Position position, int career,
                              List<ResumeTechStack> techStack) {
        this.resumeId = id;
        this.userName = username;
        this.resumeName = resumeName;
        this.position = position;
        this.career = career;
        this.techStack = techStack;
    }

    public static ResumePageElement of(Resume resume){
        return new ResumePageElement(resume.getId(), resume.getUsername(), resume.getResumeName(), resume.getPosition(),
                resume.getCareer(), resume.getResumeTechStacks());
    }
}
