package com.techeer.backend.api.feedback.converter;

import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.dto.FeedbackResponse;
import com.techeer.backend.api.resume.domain.Resume;

public class FeedbackConverter {

	public static FeedbackResponse toFeedbackResponse(Resume resume, Feedback feedback) {
		return FeedbackResponse.builder()
			.feedbackId(feedback.getId())
			.resumeId(resume.getId())
			.content(feedback.getContent())
			.xCoordinate(feedback.getXCoordinate())
			.yCoordinate(feedback.getYCoordinate())
			.build();
	}
}
