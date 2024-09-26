package com.techeer.backend.feedback.Repository;

import com.techeer.backend.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Optional<Feedback> findByIdAndResumeId(Long id, Long resumeId);
    List<Feedback> findAllByResumeId(Long resumeId);
}
