package com.techeer.backend.api.resume.repository;

import com.techeer.backend.api.resume.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByUsername(String username);
    Optional<Resume> findByIdAndDeletedAtIsNull(Long resumeId);
}
