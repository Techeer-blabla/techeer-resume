package com.techeer.backend.api.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class AllFeedbackResponse {
    private final Long feedbackId;
    private final Long resumeId;
    private final String content;
    private final Double xCoordinate;
    private final Double yCoordinate;
    private final int pageNumber;

    private final String aiFeedbackContent;
    private final Long aiFeedbackId;
}
