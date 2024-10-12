package com.techeer.backend.api.resume.service;

import org.springframework.stereotype.Service;

import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.domain.ResumeTechStack;
import com.techeer.backend.api.resume.domain.TechStack;
import com.techeer.backend.api.resume.repository.ResumeTechStackRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ResumeTechStackService {

	private final ResumeTechStackRepository resumeTechStackRepository;

	public ResumeTechStack saveResumeTechStack(Resume resume, TechStack techStack) {
		return resumeTechStackRepository.save(
			ResumeTechStack.builder()
				.resume(resume)
				.techStack(techStack)
				.build()
		);
	}

}
