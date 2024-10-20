package com.techeer.backend.api.resume.dto.response;

import java.util.List;

import com.techeer.backend.api.resume.domain.Position;

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
	private final Position position;
	private final Integer career;
	private final List<String> techStackNames;
	private List<String> applyingCompanies;
}
