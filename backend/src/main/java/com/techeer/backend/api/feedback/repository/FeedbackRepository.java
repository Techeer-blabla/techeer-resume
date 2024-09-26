package com.techeer.backend.api.feedback.repository;

import com.techeer.backend.api.feedback.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findByIdAndResumeId(Long id, Long resumeId);

}
