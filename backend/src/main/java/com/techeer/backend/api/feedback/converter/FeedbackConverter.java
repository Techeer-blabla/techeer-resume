package com.techeer.backend.api.feedback.converter;

import com.techeer.backend.api.aifeedback.domain.AIFeedback;
import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.dto.AllFeedbackResponse;
import com.techeer.backend.api.feedback.dto.FeedbackResponse;

public class FeedbackConverter {

    public static FeedbackResponse toFeedbackResponse(Feedback feedback) {
        return FeedbackResponse.builder()
                .feedbackId(feedback.getId())
                .resumeId(feedback.getResume().getId())
                .content(feedback.getContent())
                .xCoordinate(feedback.getXCoordinate())
                .yCoordinate(feedback.getYCoordinate())
                .pageNumber(feedback.getPageNumber())// AI 피드백 내용 포함
                .build();
    }

    public static AllFeedbackResponse toAllFeedbackResponse(Feedback feedback, AIFeedback aiFeedback) {
        return AllFeedbackResponse.builder()
                .feedbackId(feedback.getId())
                .resumeId(feedback.getResume().getId())
                .content(feedback.getContent())
                .xCoordinate(feedback.getXCoordinate())
                .yCoordinate(feedback.getYCoordinate())
                .pageNumber(feedback.getPageNumber())
                .aiFeedbackContent(aiFeedback.getFeedback()) // AI 피드백 내용
                .aiFeedbackId(aiFeedback.getId()) // AI 피드백 ID
                .build();
    }
}
