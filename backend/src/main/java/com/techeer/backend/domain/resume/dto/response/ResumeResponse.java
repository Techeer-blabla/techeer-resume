package com.techeer.backend.domain.resume.dto.response;

import lombok.Getter;

@Getter
public class ResumeResponse {

    private final Long resumeId;
    private final String userName;
    private final String resumeName;
    private final String fileUrl;

    public ResumeResponse(Long resumeId, String userName, String resumeName, String fileUrl) {
        this.resumeId = resumeId;
        this.userName = userName;
        this.resumeName = resumeName;
        this.fileUrl = fileUrl;
    }
}
