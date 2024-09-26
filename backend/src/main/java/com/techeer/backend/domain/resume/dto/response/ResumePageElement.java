package com.techeer.backend.domain.resume.dto.response;

import com.techeer.backend.domain.resume.entity.Resume;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ResumePageElement {
    private Long resumeId;
    private String userName;
    private String position;
    private int career;
    private List<String> techStack;

    private ResumePageElement(Long id, String username, String position, int career, List<String> techStack) {
        this.resumeId = id;
        this.userName = username;
        this.position = position;
        this.career = career;
        this.techStack = techStack;
    }

    public static ResumePageElement of(Resume resume){
        return new ResumePageElement(resume.getId(),
                resume.getUser().getUsername(), " ", resume.getCareer(), resume.getTechStack());
    }
}
