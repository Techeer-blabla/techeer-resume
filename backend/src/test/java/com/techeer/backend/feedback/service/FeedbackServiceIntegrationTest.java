package com.techeer.backend.feedback.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.dto.request.FeedbackCreateRequest;
import com.techeer.backend.api.feedback.dto.response.FeedbackResponse;
import com.techeer.backend.api.feedback.service.FeedbackService;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.user.repository.UserRepository;
import com.techeer.backend.feedback.repository.FeedbackRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class FeedbackServiceIntegrationTest {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    private Resume resume;
    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("testUser", "test@example.com", "password"));
        resume = resumeRepository.save(new Resume("Test Resume", user));
    }

    @Test
    @DisplayName("피드백 생성 - 성공")
    void createFeedback_Success() {
        // Given
        FeedbackCreateRequest request = new FeedbackCreateRequest("Excellent resume!");

        // When
        FeedbackResponse response = feedbackService.createFeedback(user, resume.getId(), request);

        // Then
        assertNotNull(response);
        assertEquals("Excellent resume!", response.getContent());
    }

    @Test
    @DisplayName("피드백 삭제 - 성공")
    void deleteFeedback_Success() {
        // Given
        Feedback feedback = feedbackRepository.save(new Feedback("Feedback to delete", resume, user));

        // When
        feedbackService.deleteFeedbackById(user, resume.getId(), feedback.getId());

        // Then
        assertFalse(feedbackRepository.findById(feedback.getId()).isPresent());
    }
}

