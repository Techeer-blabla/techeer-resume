package com.techeer.backend.api.aifeedback.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name="AIFEEDBACK")
public class AIFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long resumeId;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String feedback;

    // 기본 생성자
    protected AIFeedback() {}

    // 생성자
    @Builder
    public AIFeedback(Long resumeId, String feedback) {
        this.resumeId = resumeId;
        this.feedback = feedback;
    }
}
