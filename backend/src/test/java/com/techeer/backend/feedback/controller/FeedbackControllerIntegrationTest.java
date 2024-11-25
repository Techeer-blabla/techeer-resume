package com.techeer.backend.feedback.controller;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.dto.request.FeedbackCreateRequest;
import com.techeer.backend.api.feedback.repository.FeedbackRepository;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FeedbackControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private Resume testResume;

    @BeforeEach
    public void setup() {
        feedbackRepository.deleteAll();
        userRepository.deleteAll();
        resumeRepository.deleteAll();

        // 테스트 사용자와 이력서 생성
        testUser = userRepository.save(new User("testuser", "test@example.com"));
        testResume = resumeRepository.save(new Resume("Test Resume", testUser));
    }

    @Test
    public void shouldCreateFeedbackSuccessfully() throws Exception {
        // Given: 피드백 생성 요청 데이터 준비
        FeedbackCreateRequest request = FeedbackCreateRequest.builder()
                .content("This is a feedback")
                .xCoordinate(0.5)
                .yCoordinate(0.5)
                .build();

        // When: 피드백 생성 API 호출
        mockMvc.perform(post("/api/v1/feedbacks/resumes/" + testResume.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())  // Then: 상태 코드 201(Created) 확인
                .andExpect(jsonPath("$.content").value("This is a feedback"))  // JSON 응답 필드 검증
                .andExpect(jsonPath("$.rating").value(5));

        // Then: 데이터베이스에 피드백이 저장되었는지 확인
        List<Feedback> feedbacks = feedbackRepository.findAll();
        assertThat(feedbacks).hasSize(1);
        Feedback savedFeedback = feedbacks.get(0);
        assertThat(savedFeedback.getContent()).isEqualTo("This is a feedback");
        assertThat(savedFeedback.getXCoordinate()).isEqualTo(0.5);
        assertThat(savedFeedback.getYCoordinate()).isEqualTo(0.5);
    }

    @Test
    public void shouldReturnAllFeedbacksForResume() throws Exception {
        // Given: 두 개의 피드백 생성
        feedbackRepository.save(new Feedback("Feedback 1", 0.1, 0.2, 5, testResume, testUser));
        feedbackRepository.save(new Feedback("Feedback 2", 0.3, 0.4, 4, testResume, testUser));

        // When: 피드백 조회 API 호출
        mockMvc.perform(get("/api/v1/feedbacks/resumes/" + testResume.getId()))
                .andExpect(status().isOk())  // Then: 상태 코드 200(OK) 확인
                .andExpect(jsonPath("$.length()").value(2))  // 응답 크기 확인
                .andExpect(jsonPath("$[0].content").value("Feedback 1"))
                .andExpect(jsonPath("$[1].content").value("Feedback 2"));
    }

    @Test
    public void shouldDeleteFeedbackSuccessfully() throws Exception {
        // Given: 삭제할 피드백 생성
        Feedback feedback = feedbackRepository.save(new Feedback("Test Feedback", 0.5, 0.5, 5, testResume, testUser));

        // When: 피드백 삭제 API 호출
        mockMvc.perform(delete("/api/v1/feedbacks/" + feedback.getId()))
                .andExpect(status().isNoContent());  // Then: 상태 코드 204(No Content) 확인

        // Then: 데이터베이스에서 피드백이 삭제되었는지 확인
        Optional<Feedback> deletedFeedback = feedbackRepository.findById(feedback.getId());
        assertThat(deletedFeedback).isEmpty();
    }
}
