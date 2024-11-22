package com.techeer.backend.feedback.repository;

import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.repository.FeedbackRepository;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("피드백 조회 - 성공")
    void findByResumeId_Success() {
        // Given
        User user = userRepository.save(new User("testUser", "test@example.com", "password"));
        Resume resume = resumeRepository.save(new Resume("Test Resume", user));
        Feedback feedback = feedbackRepository.save(new Feedback("Test Feedback", resume, user));

        // When
        List<Feedback> feedbacks = feedbackRepository.findAllByResumeId(resume.getId());

        // Then
        assertEquals(1, feedbacks.size());
        assertEquals("Test Feedback", feedbacks.get(0).getContent());
    }
}

