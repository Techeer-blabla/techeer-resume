package com.techeer.backend;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.backend.api.aifeedback.domain.AIFeedback;
import com.techeer.backend.api.feedback.controller.FeedbackController;
import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.dto.request.FeedbackCreateRequest;
import com.techeer.backend.api.feedback.service.FeedbackService;
import com.techeer.backend.api.user.domain.Role;
import com.techeer.backend.api.user.domain.SocialType;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FeedbackController.class) // FeedbackController 클래스만 로드하는 WebMvcTest
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc를 통해 HTTP 요청/응답을 모의

    @Autowired
    private ObjectMapper objectMapper; // 객체를 JSON으로 직렬화/역직렬화하기 위한 ObjectMapper

    @MockBean
    private FeedbackService feedbackService; // 서비스 레이어는 Mock 처리하여 컨트롤러 테스트에 집중

    @MockBean
    private UserService userService; // 로그인 사용자 정보를 제공하는 UserService도 Mock 처리

    @Nested
    @DisplayName("POST /api/v1/resumes/{resume_id}/feedbacks")
    class CreateFeedback {

        @Test
        @DisplayName("유효한 피드백 데이터로 POST 요청하면 CREATED 상태(201)와 올바른 응답 반환")
        void createFeedback_Success() throws Exception {
            // Given: 이력서 ID와 유저 세팅
            Long resumeId = 1L;
            String userName = "JohnDoe";
            User mockUser = User.builder()
                    .email("john@example.com")
                    .username(userName)
                    .refreshToken(null)
                    .role(Role.TECHEER)
                    .socialType(SocialType.GOOGLE)
                    .build();

            // 요청용 DTO 생성: content와 좌표 등이 유효한 데이터
            FeedbackCreateRequest request = new FeedbackCreateRequest(
                    "This is feedback",
                    100.5,
                    200.5,
                    null,
                    null,
                    1
            );

            // 서비스에서 반환할 Mock Feedback 엔티티 세팅
            Feedback mockFeedback = Feedback.builder()
                    .id(10L)
                    .content(request.getContent())
                    .xCoordinate(request.getXCoordinate())
                    .yCoordinate(request.getYCoordinate())
                    .pageNumber(request.getPageNumber())
                    .build();

            // userService, feedbackService에 대한 Mock 동작 정의
            Mockito.when(userService.getLoginUser()).thenReturn(mockUser);
            Mockito.when(feedbackService.createFeedback(eq(mockUser), eq(resumeId), any(FeedbackCreateRequest.class)))
                    .thenReturn(mockFeedback);

            // When & Then: POST 요청을 보내고, 기대하는 JSON 응답 필드를 검증
            // 응답: code: "피드백 생성 성공", message: "FEEDBACK_201" 등을 가정
            mockMvc.perform(post("/api/v1/resumes/{resume_id}/feedbacks", resumeId)
                            .contentType(MediaType.APPLICATION_JSON) // JSON 요청
                            .content(objectMapper.writeValueAsString(request))) // Body에 request JSON 직렬화
                    .andExpect(status().isCreated()) // HTTP 201 상태 기대
                    .andExpect(jsonPath("$.code").value("피드백 생성 성공"))
                    .andExpect(jsonPath("$.message").value("FEEDBACK_201"))
                    .andExpect(jsonPath("$.result.id").value(mockFeedback.getId()))
                    .andExpect(jsonPath("$.result.content").value(mockFeedback.getContent()))
                    .andExpect(jsonPath("$.result.xCoordinate").value(mockFeedback.getXCoordinate()))
                    .andExpect(jsonPath("$.result.yCoordinate").value(mockFeedback.getYCoordinate()))
                    .andExpect(jsonPath("$.result.pageNumber").value(mockFeedback.getPageNumber()));
        }

        @Test
        @DisplayName("유효하지 않은 피드백 데이터로 POST 요청하면 BAD_REQUEST(400) 반환")
        void createFeedback_InvalidData() throws Exception {
            // Given: 유효하지 않은 데이터, 예: content가 빈 문자열
            Long resumeId = 1L;
            String userName = "JohnDoe";
            User mockUser = User.builder()
                    .email("john@example.com")
                    .username(userName)
                    .refreshToken(null)
                    .role(Role.TECHEER)
                    .socialType(SocialType.GOOGLE)
                    .build();

            FeedbackCreateRequest invalidRequest = new FeedbackCreateRequest(
                    "",
                    100.0,
                    200.0,
                    null,
                    null,
                    1
            );

            Mockito.when(userService.getLoginUser()).thenReturn(mockUser);

            // When & Then: 빈 content로 요청 시 검증 실패 -> 400 오류 기대
            mockMvc.perform(post("/api/v1/resumes/{resume_id}/feedbacks", resumeId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("GET /api/v1/resumes/{resume_id}/feedbacks")
    class GetFeedbacks {

        @Test
        @DisplayName("AI 및 일반 피드백이 있을 경우 OK와 해당 피드백 정보 반환")
        void getFeedbackWithAIFeedback_Success() throws Exception {
            // Given: resumeId에 해당하는 일반 피드백 2개와 AI 피드백 1개가 존재한다고 가정
            Long resumeId = 1L;

            Feedback feedback1 = Feedback.builder()
                    .id(10L)
                    .content("Good structure")
                    .xCoordinate(50.0)
                    .yCoordinate(60.0)
                    .pageNumber(1)
                    .build();

            Feedback feedback2 = Feedback.builder()
                    .id(11L)
                    .content("Improve formatting")
                    .xCoordinate(70.0)
                    .yCoordinate(80.0)
                    .pageNumber(2)
                    .build();

            AIFeedback aiFeedback = AIFeedback.builder()
                    .resumeId(resumeId)
                    .feedback("AI recommended: add more detail")
                    .build();

            // Mock 설정: 서비스에서 resumeId에 대한 피드백 리스트와 AI 피드백 반환
            Mockito.when(feedbackService.getFeedbackByResumeId(resumeId)).thenReturn(List.of(feedback1, feedback2));
            Mockito.when(feedbackService.getAIFeedbackByResumeId(resumeId)).thenReturn(aiFeedback);

            // When & Then: GET 요청 시, JSON 응답의 필드 확인
            mockMvc.perform(get("/api/v1/resumes/{resume_id}/feedbacks", resumeId))
                    .andExpect(status().isOk()) // 200 상태 기대
                    .andExpect(jsonPath("$.code").value("피드백 조회 성공"))
                    .andExpect(jsonPath("$.message").value("FEEDBACK_200"))
                    .andExpect(jsonPath("$.result.ai_feedback_content").value(aiFeedback.getFeedback()))
                    .andExpect(jsonPath("$.result.ai_feedback_id").value(
                            aiFeedback.getId())) // aiFeedback.getId()는 실제 구현에 따라 null일 수도 있으니 테스트 상황에 맞게 수정 필요
                    .andExpect(jsonPath("$.result.feedback_responses", hasSize(2))) // 피드백 2개
                    .andExpect(jsonPath("$.result.feedback_responses[0].content").value(feedback1.getContent()))
                    .andExpect(jsonPath("$.result.feedback_responses[0].xCoordinate").value(feedback1.getXCoordinate()))
                    .andExpect(jsonPath("$.result.feedback_responses[0].yCoordinate").value(feedback1.getYCoordinate()))
                    .andExpect(jsonPath("$.result.feedback_responses[0].pageNumber").value(feedback1.getPageNumber()))
                    .andExpect(jsonPath("$.result.feedback_responses[1].content").value(feedback2.getContent()))
                    .andExpect(jsonPath("$.result.feedback_responses[1].xCoordinate").value(feedback2.getXCoordinate()))
                    .andExpect(jsonPath("$.result.feedback_responses[1].yCoordinate").value(feedback2.getYCoordinate()))
                    .andExpect(jsonPath("$.result.feedback_responses[1].pageNumber").value(feedback2.getPageNumber()));
        }

        @Test
        @DisplayName("피드백이 없을 경우 OK 상태와 빈 리스트 응답")
        void getFeedbackWithAIFeedback_Empty() throws Exception {
            // Given: 해당 resumeId에 대해 피드백 및 AI 피드백이 없는 상황
            Long resumeId = 1L;
            Mockito.when(feedbackService.getFeedbackByResumeId(resumeId)).thenReturn(List.of());
            Mockito.when(feedbackService.getAIFeedbackByResumeId(resumeId)).thenReturn(null);

            // When & Then: 빈 리스트 기대
            mockMvc.perform(get("/api/v1/resumes/{resume_id}/feedbacks", resumeId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value("피드백 조회 성공"))
                    .andExpect(jsonPath("$.message").value("FEEDBACK_200"))
                    .andExpect(jsonPath("$.result.feedback_responses", hasSize(0))) // 빈 배열
                    .andExpect(jsonPath("$.result.ai_feedback_content").doesNotExist()) // AI 피드백 없음
                    .andExpect(jsonPath("$.result.ai_feedback_id").doesNotExist());
        }
    }

    @Nested
    @DisplayName("DELETE /api/v1/resumes/{resume_id}/feedbacks/{feedback_id}")
    class DeleteFeedback {

        @Test
        @DisplayName("삭제할 피드백이 존재하면 NO_CONTENT(204) 반환")
        void deleteFeedback_Success() throws Exception {
            // Given: 특정 resumeId와 feedbackId에 대한 피드백 삭제 요청
            Long resumeId = 1L;
            Long feedbackId = 10L;
            String userName = "JohnDoe";
            User mockUser = User.builder()
                    .email("john@example.com")
                    .username(userName)
                    .refreshToken(null)
                    .role(Role.TECHEER)
                    .socialType(SocialType.GOOGLE)
                    .build();

            Mockito.when(userService.getLoginUser()).thenReturn(mockUser);
            Mockito.doNothing().when(feedbackService).deleteFeedbackById(mockUser, resumeId, feedbackId);

            // When & Then: 삭제 성공 시 204 반환
            mockMvc.perform(delete("/api/v1/resumes/{resume_id}/feedbacks/{feedback_id}", resumeId, feedbackId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("존재하지 않는 피드백 삭제 요청시 BAD_REQUEST(400) 반환")
        void deleteFeedback_NotFound() throws Exception {
            // Given: 없는 feedbackId 삭제 요청 -> 예외 발생 가정
            Long resumeId = 1L;
            Long feedbackId = 999L;
            String userName = "JohnDoe";
            User mockUser = User.builder()
                    .email("john@example.com")
                    .username(userName)
                    .refreshToken(null)
                    .role(Role.TECHEER)
                    .socialType(SocialType.GOOGLE)
                    .build();

            Mockito.when(userService.getLoginUser()).thenReturn(mockUser);
            Mockito.doThrow(new IllegalArgumentException("Feedback not found"))
                    .when(feedbackService).deleteFeedbackById(mockUser, resumeId, feedbackId);

            // When & Then: 존재하지 않는 피드백 -> 400 반환
            mockMvc.perform(delete("/api/v1/resumes/{resume_id}/feedbacks/{feedback_id}", resumeId, feedbackId))
                    .andExpect(status().isBadRequest());
        }
    }
}
