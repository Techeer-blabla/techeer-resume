package com.techeer.backend.api.feedback.converter;

import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.dto.FeedbackResponse;

public class FeedbackConverter {

    public static FeedbackResponse toFeedbackResponse(Feedback feedback) {
        return FeedbackResponse.builder()
                .feedbackId(feedback.getId())
                .content(feedback.getContent())
                .xCoordinate(feedback.getXCoordinate())
                .yCoordinate(feedback.getYCoordinate())
                .pageNumber(feedback.getPageNumber())
                .build();
    }
}
