package com.techeer.backend.feedback.service;

import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.dto.request.FeedbackCreateRequest;
import com.techeer.backend.api.feedback.repository.FeedbackRepository;
import com.techeer.backend.api.feedback.service.FeedbackService;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class FeedbackServiceIntegrationTest {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    private User testUser;
    private Resume testResume;

    @BeforeEach
    public void setup() {
        feedbackRepository.deleteAll();
        userRepository.deleteAll();
        resumeRepository.deleteAll();

        testUser = userRepository.save(new User("testuser", "test@example.com"));
        testResume = resumeRepository.save(new Resume("Test Resume", testUser));
    }

    @Test
    public void shouldCreateFeedbackSuccessfully() {
        // Given: 피드백 생성 요청 데이터 준비
        FeedbackCreateRequest request = FeedbackCreateRequest.builder()
                .content("This is a feedback")
                .xCoordinate(0.5)
                .yCoordinate(0.5)
                .rating(5)
                .build();

        // When: 피드백 생성 서비스 호출
        Feedback feedback = feedbackService.createFeedback(testUser, testResume.getId(), request);

        // Then: 피드백 저장 상태 검증
        assertThat(feedback).isNotNull();
        assertThat(feedback.getContent()).isEqualTo("This is a feedback");
        assertThat(feedback.getXCoordinate()).isEqualTo(0.5);
        assertThat(feedback.getYCoordinate()).isEqualTo(0.5);
        assertThat(feedback.getRating()).isEqualTo(5);
    }

    @Test
    public void shouldGetAllFeedbacksForResume() {
        // Given: 피드백 두 개 생성
        feedbackRepository.save(new Feedback("Feedback 1", 0.1, 0.2, 5, testResume, testUser));
        feedbackRepository.save(new Feedback("Feedback 2", 0.3, 0.4, 4, testResume, testUser));

        // When: 피드백 조회 서비스 호출
        List<Feedback> feedbacks = feedbackService.getFeedbacksByResumeId(testResume.getId());

        // Then: 조회된 피드백 검증
        assertThat(feedbacks).hasSize(2);
        assertThat(feedbacks.get(0).getContent()).isEqualTo("Feedback 1");
        assertThat(feedbacks.get(1).getContent()).isEqualTo("Feedback 2");
    }

    @Test
    public void shouldDeleteFeedbackSuccessfully() {
        // Given: 삭제할 피드백 생성
        Feedback feedback = feedbackRepository.save(new Feedback("Test Feedback", 0.5, 0.5, 5, testResume, testUser));

        // When: 피드백 삭제 서비스 호출
        feedbackService.deleteFeedbackById(testUser, testResume.getId(), feedback.getId());

        // Then: 데이터베이스에서 피드백 삭제 검증
        Optional<Feedback> deletedFeedback = feedbackRepository.findById(feedback.getId());
        assertThat(deletedFeedback).isEmpty();
    }
}
