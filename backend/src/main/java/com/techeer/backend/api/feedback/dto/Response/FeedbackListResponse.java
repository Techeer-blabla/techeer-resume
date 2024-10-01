package com.techeer.backend.api.feedback.dto.Response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.backend.api.feedback.domain.Feedback;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@RequiredArgsConstructor
@Getter
public class FeedbackListResponse {

    private Long resumeId;

    private List<FeedbackResponse> feedbacks;

    private FeedbackListResponse(Long resumeId, List<FeedbackResponse> feedbacks){
        this.resumeId = resumeId;
        this.feedbacks = feedbacks;
    }

    public static  FeedbackListResponse from(Long resumeId, List<FeedbackResponse> feedbacks){
        return new FeedbackListResponse(resumeId, feedbacks);
    }
}
