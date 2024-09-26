package com.techeer.backend.domain.resume.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.backend.domain.resume.entity.Resume;
import com.techeer.backend.feedback.entity.Feedback;
import lombok.Getter;

import java.util.List;

//todo 피드백 까지 생기면 생성
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FetchResumeContentResponse {

    private final Long resumeId;
    private final String userName;
    private final String position;
    private final int career;
    private final List<String> techStack;
    private final String fileUrl;
    private final List<Feedback> feedbacks;

    private FetchResumeContentResponse(Long id, String username, String position, int career,
                                       List<String> techStack, String fileUrl, List<Feedback> feedbacks) {
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
                resume.getUser().getUsername(), " ", resume.getCareer(), resume.getTechStack(),
                resume.getUrl(), feedbacks);
    }

}

