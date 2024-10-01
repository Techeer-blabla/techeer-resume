package com.techeer.backend.api.resume.dto.response;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.backend.api.feedback.dto.Response.FeedbackResponse;
import com.techeer.backend.api.resume.domain.Position;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.domain.ResumeTechStack;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FetchResumeContentResponse {

    private Long resumeId;
    private String userName;
    private Position position;
    private int career;
    private List<ResumeTechStack> techStack;
    private String fileUrl;
    private List<FeedbackResponse> feedbacks;


//    public static FetchResumeContentResponse from(Resume resume, List<FeedbackResponse> feedbacks){
//        return new FetchResumeContentResponse(resume.getId(),
//                resume.getUsername(), resume.getPosition(), resume.getCareer(), resume.getResumeTechStacks(),
//                resume.getUrl(), feedbacks);
//    }




}

