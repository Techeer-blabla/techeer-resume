package com.techeer.backend.api.resume.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.resume.domain.Company;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.dto.request.CreateResumeRequest;
import com.techeer.backend.api.resume.dto.response.FetchResumeContentResponse;
import com.techeer.backend.api.resume.dto.response.ResumeResponse;

public class ResumeConverter {

	public static Resume toResume(CreateResumeRequest createResumeRequest) {
		return Resume.builder()
			.username(createResumeRequest.getUsername())
			.position(createResumeRequest.getPosition())
			.career(createResumeRequest.getCareer())
			.name("Resume of " + createResumeRequest.getUsername())
			.resumeTechStacks(new ArrayList<>())
			.companies(new ArrayList<>())
			.build();
	}

	public static ResumeResponse toResumeResponse(Resume resume) {
		return ResumeResponse.builder()
			.resumeId(resume.getId())
			.userName(resume.getUsername())  // User 객체에서 username 추출
			.resumeName(resume.getName())
			.fileUrl(resume.getUrl())
			.position(String.valueOf(resume.getPosition()))
			.career(resume.getCareer())
			.applyingCompanies(resume.getCompanies().stream()
				.map(Company::getName)  // Map each Company to its name
				.collect(Collectors.toList()))
			.techStackNames(resume.getResumeTechStacks().stream()
				.map(resumeTechStack -> resumeTechStack.getTechStack().getName())  // ResumeTechStack 객체에서 기술 스택 이름 추출
				.collect(Collectors.toList()))  // 기술 스택 이름 리스트로 변환
			.build();
	}

	public static FetchResumeContentResponse toFetchResumeContentResponse(Resume resume, List<Feedback> feedbacks) {
		return FetchResumeContentResponse.builder()
			.resumeId(resume.getId())
			.userName(resume.getName())
			.position(resume.getPosition())
			.career(resume.getCareer())
			.techStacks(resume.getResumeTechStacks().stream()
				.map(resumeTechStack -> resumeTechStack.getTechStack().getName())
				.collect(Collectors.toList()))
			.fileUrl(resume.getUrl())
			.feedbacks(feedbacks)
			.build();
	}

}
