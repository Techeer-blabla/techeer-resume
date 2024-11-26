package com.techeer.backend.api.feedback.converter;

import com.techeer.backend.api.aifeedback.domain.AIFeedback;
import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.dto.request.FeedbackCreateRequest;
import com.techeer.backend.api.feedback.dto.response.AllFeedbackResponse;
import com.techeer.backend.api.feedback.dto.response.FeedbackResponse;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.user.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public static AllFeedbackResponse toAllFeedbackResponse(List<Feedback> feedbacks, AIFeedback aiFeedback) {
        return AllFeedbackResponse.builder()
                .feedbackResponses(convertFeedbacksToResponses(feedbacks))
                .aiFeedbackContent(Optional.ofNullable(aiFeedback).map(AIFeedback::getFeedback).orElse(null))
                .aiFeedbackId(Optional.ofNullable(aiFeedback).map(AIFeedback::getId).orElse(null))
                .build();
    }

    private static List<FeedbackResponse> convertFeedbacksToResponses(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .map(feedback -> FeedbackResponse.builder()
                        .feedbackId(feedback.getId())
                        .resumeId(feedback.getResume().getId())
                        .content(feedback.getContent())
                        .xCoordinate(feedback.getXCoordinate())
                        .yCoordinate(feedback.getYCoordinate())
                        .pageNumber(feedback.getPageNumber())
                        .build())
                .collect(Collectors.toList());
    }

    public static Feedback toFeedbackEntity(User user, Resume resume, FeedbackCreateRequest feedbackCreateRequest) {
        return Feedback.builder()
                .user(user)
                .resume(resume)
                .content(feedbackCreateRequest.getContent())
                .xCoordinate(feedbackCreateRequest.getXCoordinate())
                .yCoordinate(feedbackCreateRequest.getYCoordinate())
                .build();
    }
}
