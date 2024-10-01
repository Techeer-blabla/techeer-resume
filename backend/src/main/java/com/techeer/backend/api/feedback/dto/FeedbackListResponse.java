package com.techeer.backend.api.feedback.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.backend.api.feedback.domain.Feedback;
import lombok.Getter;

import java.util.List;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FeedbackListResponse {

    private Long resumeId;

    private List<Feedback> feedbacks;

    private FeedbackListResponse(Long resumeId, List<Feedback> feedbacks){
        this.resumeId = resumeId;
        this.feedbacks = feedbacks;
    }

    public static  FeedbackListResponse from(Long resumeId, List<Feedback> feedbacks){
        return new FeedbackListResponse(resumeId, feedbacks);
    }
}
