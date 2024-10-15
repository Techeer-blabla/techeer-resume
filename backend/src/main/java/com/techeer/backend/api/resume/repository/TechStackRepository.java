package com.techeer.backend.api.resume.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techeer.backend.api.resume.domain.TechStack;

@Repository
public interface TechStackRepository extends JpaRepository<TechStack, Long> {
	Optional<TechStack> findByName(String techStackName);
}
