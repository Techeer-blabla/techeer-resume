package com.techeer.backend.api.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techeer.backend.api.resume.domain.ResumeTechStack;

@Repository
public interface ResumeTechStackRepository extends JpaRepository<ResumeTechStack, Long> {
}
