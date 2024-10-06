package com.techeer.backend.api.aifeedback.dto;

import com.techeer.backend.api.aifeedback.domain.AIFeedback;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AIFeedbackResponse {

    private final Long id;
    private final Long resumeId;
    private final String feedback;

    @Builder
    public AIFeedbackResponse(Long id, Long resumeId, String feedback) {
        this.id = id;
        this.resumeId = resumeId;
        this.feedback = feedback;
    }

    // 정적 팩토리 메서드: AIFeedback 엔티티에서 데이터를 받아 DTO로 변환
    public static AIFeedbackResponse of(AIFeedback aiFeedback) {
        return AIFeedbackResponse.builder()
                .id(aiFeedback.getId())
                .resumeId(aiFeedback.getResumeId())
                .feedback(aiFeedback.getFeedback())
                .build();
    }
}
