package com.techeer.backend.domain.feedback.Repository;

import com.techeer.backend.domain.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findByIdAndResumeId(Long id, Long resumeId);

}
