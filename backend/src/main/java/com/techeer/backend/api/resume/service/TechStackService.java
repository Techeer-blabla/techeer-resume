package com.techeer.backend.api.resume.service;

import org.springframework.stereotype.Service;

import com.techeer.backend.api.resume.domain.TechStack;
import com.techeer.backend.api.resume.repository.TechStackRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TechStackService {
	private final TechStackRepository techStackRepository;

	@Transactional
	public TechStack saveTechStack(String techStackName) {
		return techStackRepository.save(
			TechStack.builder()
				.name(techStackName)
				.build()
		);
	}
}
