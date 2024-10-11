package com.techeer.backend.api.resume.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
// @AllArgsConstructor
@Builder
public class ResumeResponse {
	private final Long resumeId;
	private final String userName;
	private final String resumeName;
	private final String fileUrl;
	private final String position;
	private final Integer career;
	private final List<String> techStackNames;

}
