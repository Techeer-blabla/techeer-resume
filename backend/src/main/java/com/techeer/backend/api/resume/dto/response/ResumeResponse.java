package com.techeer.backend.api.resume.dto.response;

import com.techeer.backend.api.resume.domain.Resume;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResumeResponse {
    private final Long resumeId;
    private final String userName;
    private final String resumeName;
    private final String fileUrl;

    public static ResumeResponse from(Resume resume) {
        return new ResumeResponse(
                resume.getId(),
                resume.getUsername(),
                resume.getResumeName(),
                resume.getUrl()
        );
    }
}
