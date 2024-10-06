package com.techeer.backend.api.feedback.domain;

import java.math.BigDecimal;

import com.techeer.backend.api.feedback.dto.FeedbackCreateRequest;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

	@Column(precision = 10, scale = 6)
	private BigDecimal xCoordinate;

	@Column(nullable = false, precision = 10, scale = 6)
	private BigDecimal yCoordinate;

	// 기본 생성자
	protected Feedback() {
	}

	// Builder 패턴을 사용한 생성자
	@Builder
	public static Feedback of(Resume resume, FeedbackCreateRequest feedbackCreateRequest) {
		return Feedback.builder()
			.resume(resume)
			.content(feedbackCreateRequest.getContent())
			.xCoordinate(feedbackCreateRequest.getXCoordinate())
			.yCoordinate(feedbackCreateRequest.getYCoordinate())
			.build();
	}
}
