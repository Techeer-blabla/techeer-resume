package com.techeer.backend.api.feedback.domain;

import com.techeer.backend.api.feedback.dto.FeedbackCreateRequest;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "FEEDBACK")
@Builder
@AllArgsConstructor
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

	@Column(nullable = false)
	private Double xCoordinate;

	@Column(nullable = false)
	private Double yCoordinate;

	@Column(nullable = false)
	private int pageNumber;

	// 기본 생성자
	protected Feedback() {
	}

	// Builder 패턴을 사용한 생성자
	@Builder
	public static Feedback of(Resume resume, FeedbackCreateRequest feedbackCreateRequest) {
		if (feedbackCreateRequest.getPageNumber() <= 0) {
			throw new IllegalArgumentException("페이지번호는 양수입니다.");
		}
    return Feedback.builder()
        .resume(resume)
        .content(feedbackCreateRequest.getContent())
        .xCoordinate(feedbackCreateRequest.getXCoordinate())
        .yCoordinate(feedbackCreateRequest.getYCoordinate())
        .pageNumber(feedbackCreateRequest.getPageNumber())
        .build();
  }
}
