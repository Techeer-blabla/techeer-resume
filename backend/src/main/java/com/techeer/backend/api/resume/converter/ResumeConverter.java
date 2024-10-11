package com.techeer.backend.api.resume.converter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.dto.response.ResumeResponse;

@Component
public class ResumeConverter {

	public ResumeResponse from(Resume resume) {
		return ResumeResponse.builder()
			.resumeId(resume.getId())
			.userName(resume.getUsername())  // User 객체에서 username 추출
			.resumeName(resume.getName())
			.fileUrl(resume.getUrl())
			.position(String.valueOf(resume.getPosition()))
			.career(resume.getCareer())
			.techStackNames(resume.getResumeTechStacks().stream()
				.map(resumeTechStack -> resumeTechStack.getTechStack().getName())  // ResumeTechStack 객체에서 기술 스택 이름 추출
				.collect(Collectors.toList()))  // 기술 스택 이름 리스트로 변환
			.build();
	}

}
