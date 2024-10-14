package com.techeer.backend.api.resume.dto.response;

import java.util.List;

import com.techeer.backend.api.resume.domain.Position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ResumePageResponse {

	private Long resumeId;
	private String userName;
	private String resumeName;
	private Position position;
	private int career;
	private List<String> techStackNames;
	private int totalPage;
	private int currentPage;
}
