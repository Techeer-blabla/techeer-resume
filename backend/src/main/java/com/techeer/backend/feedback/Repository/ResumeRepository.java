package com.techeer.backend.feedback.Repository;


import com.techeer.backend.feedback.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
