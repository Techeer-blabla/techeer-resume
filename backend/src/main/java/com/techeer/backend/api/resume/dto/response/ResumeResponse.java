package com.techeer.backend.api.resume.dto.response;

import com.techeer.backend.api.resume.domain.Resume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
@AllArgsConstructor
public class ResumeResponse {
    private Long resumeId;
    private String userName;
    private String resumeName;
    private String fileUrl;

    public static ResumeResponse of(Resume resume) {
        return new ResumeResponse(
                resume.getId(),
                resume.getUsername(),
                resume.getResumeName(),
                resume.getUrl()
        );
    }
}
