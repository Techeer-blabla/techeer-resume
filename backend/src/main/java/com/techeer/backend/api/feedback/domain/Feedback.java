package com.techeer.backend.api.feedback.domain;

import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "FEEDBACK")
public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 한 개의 이력서가 여러 개의 피드백을 가질 수 있음.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(nullable = false, length = 255)
    private String content;

    @Column(precision = 10, scale = 6)
    private BigDecimal xCoordinate;

    @Column(nullable = false,precision = 10, scale = 6)
    private BigDecimal yCoordinate;

    // 기본 생성자
    protected Feedback() {
    }

    // 모든 필드를 포함한 생성자
    public Feedback(Resume resume, String content, BigDecimal xCoordinate, BigDecimal yCoordinate) {
        this.resume = resume;
        this.content = content;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    // Builder 패턴을 사용한 생성자
    @Builder
    public static Feedback createFeedback(Resume resume, String content, BigDecimal xCoordinate, BigDecimal yCoordinate) {
        return new Feedback(resume, content, xCoordinate, yCoordinate);
    }
}
