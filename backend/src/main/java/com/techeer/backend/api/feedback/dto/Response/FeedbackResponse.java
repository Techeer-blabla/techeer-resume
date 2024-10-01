package com.techeer.backend.api.feedback.dto.Response;

import com.techeer.backend.api.feedback.domain.Feedback;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@RequiredArgsConstructor
public class FeedbackResponse {

    private final Long feedbackId;
    private final Long resumeId;
    private final String content;
    private final BigDecimal xCoordinate;
    private final BigDecimal yCoordinate;

    private FeedbackResponse(Feedback feedback) {
        this.feedbackId = feedback.getId();
        this.resumeId = feedback.getResume().getId();
        this.content = feedback.getContent();
        this.xCoordinate = feedback.getXCoordinate();
        this.yCoordinate = feedback.getYCoordinate();
    }

//    public static FeedbackResponse from(Long feedbackId, Long resumeId, String content, BigDecimal xCoordinate, BigDecimal yCoordinate) {
//        return FeedbackResponse.builder()
//                .feedbackId(feedbackId)
//                .resumeId(resumeId)
//                .content(content)
//                .xCoordinate(xCoordinate)
//                .yCoordinate(yCoordinate)
//                .build();
//    }

    public static FeedbackResponse of(Feedback feedback){
        return new FeedbackResponse(feedback);
    }

}
