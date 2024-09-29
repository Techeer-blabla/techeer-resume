package com.techeer.backend.api.resume.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.resume.domain.Position;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.domain.ResumeTechStack;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FetchResumeContentResponse {

    private final Long resumeId;
    private final String userName;
    private final Position position;
    private final int career;
    private final List<ResumeTechStack> techStack;
    private final String fileUrl;
    private final List<Feedback> feedbacks;

    private FetchResumeContentResponse(Long id, String username, Position position, int career,
                                       List<ResumeTechStack> techStack, String fileUrl, List<Feedback> feedbacks) {
        this.resumeId = id;
        this.userName = username;
        this.position = position;
        this.career = career;
        this.techStack = techStack;
        this.fileUrl = fileUrl;
        this.feedbacks = feedbacks;
    }

    public static FetchResumeContentResponse from(Resume resume, List<Feedback> feedbacks){
        return new FetchResumeContentResponse(resume.getId(),
                resume.getUsername(), resume.getPosition(), resume.getCareer(), resume.getResumeTechStacks(),
                resume.getUrl(), feedbacks);
    }

}

