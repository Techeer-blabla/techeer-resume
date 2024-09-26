package com.techeer.backend.domain.feedback.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FeedbackResponse {

    private final Long feedbackId;
    private final Long resumeId;
    private final String content;
    private final BigDecimal xCoordinate;
    private final BigDecimal yCoordinate;

    @Builder
    private FeedbackResponse(Long feedbackId, Long resumeId, String content, BigDecimal xCoordinate, BigDecimal yCoordinate) {
        this.feedbackId = feedbackId;
        this.resumeId = resumeId;
        this.content = content;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public static FeedbackResponse of(Long feedbackId, Long resumeId, String content, BigDecimal xCoordinate, BigDecimal yCoordinate) {
        return FeedbackResponse.builder()
                .feedbackId(feedbackId)
                .resumeId(resumeId)
                .content(content)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .build();
    }
}
