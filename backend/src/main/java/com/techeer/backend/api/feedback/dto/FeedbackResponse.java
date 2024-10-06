package com.techeer.backend.api.feedback.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FeedbackResponse {

	private final Long feedbackId;
	private final Long resumeId;
	private final String content;
	private final BigDecimal xCoordinate;
	private final BigDecimal yCoordinate;

}
