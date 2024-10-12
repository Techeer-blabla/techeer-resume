package com.techeer.backend.api.resume.dto.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.resume.domain.Position;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FetchResumeContentResponse {

	private final Long resumeId;
	private final String userName;
	private final Position position;
	private final int career;
	private final List<String> techStacks;
	private final String fileUrl;
	private final List<Feedback> feedbacks;

}

