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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FeedbackService feedbackService;

    @MockBean
    private UserService userService;

    @Nested
    @DisplayName("POST /api/v1/resumes/{resume_id}/feedbacks")
    class CreateFeedback {

        @Test
        @DisplayName("유효한 피드백 데이터로 POST 요청하면 CREATED 상태(201) 코드와 올바른 응답 반환")
        void createFeedback_Success() throws Exception {
            Long resumeId = 1L;
            String userName = "JohnDoe";

            User mockUser = User.builder()
                    .email("john@example.com")
                    .username(userName)
                    .refreshToken(null)
                    .role(Role.TECHEER)
                    .socialType(SocialType.GOOGLE)
                    .build();

            FeedbackCreateRequest request = new FeedbackCreateRequest(
                    "This is feedback",
                    100.5,
                    200.5,
                    null,
                    null,
                    1
            );

            Feedback mockFeedback = Feedback.builder()
                    .id(10L)
                    .content(request.getContent())
                    .xCoordinate(request.getXCoordinate())
                    .yCoordinate(request.getYCoordinate())
                    .pageNumber(request.getPageNumber())
                    .build();

            Mockito.when(userService.getLoginUser()).thenReturn(mockUser);
            Mockito.when(feedbackService.createFeedback(eq(mockUser), eq(resumeId), any(FeedbackCreateRequest.class)))
                    .thenReturn(mockFeedback);

            // 실제 응답 형식을 확인해야 하나, 여기서는 가정:
            // 성공 시 code: "피드백 생성 성공", message: "FEEDBACK_201" 과 같은 형태로 응답한다고 가정
            mockMvc.perform(post("/api/v1/resumes/{resume_id}/feedbacks", resumeId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.code").value("피드백 생성 성공"))
                    .andExpect(jsonPath("$.message").value("FEEDBACK_201"))
                    .andExpect(jsonPath("$.result.id").value(mockFeedback.getId()))
                    .andExpect(jsonPath("$.result.content").value(mockFeedback.getContent()))
                    .andExpect(jsonPath("$.result.xCoordinate").value(mockFeedback.getXCoordinate()))
                    .andExpect(jsonPath("$.result.yCoordinate").value(mockFeedback.getYCoordinate()))
                    .andExpect(jsonPath("$.result.pageNumber").value(mockFeedback.getPageNumber()));
        }

        @Test
        @DisplayName("잘못된 피드백 데이터로 POST 요청하면 BAD_REQUEST(400) 반환")
        void createFeedback_InvalidData() throws Exception {
            Long resumeId = 1L;
            String userName = "JohnDoe";

            User mockUser = User.builder()
                    .email("john@example.com")
                    .username(userName)
                    .refreshToken(null)
                    .role(Role.TECHEER)
                    .socialType(SocialType.GOOGLE)
                    .build();

            // content가 비어있는 경우 -> 유효성 검증 실패
            FeedbackCreateRequest invalidRequest = new FeedbackCreateRequest(
                    "",
                    100.0,
                    200.0,
                    null,
                    null,
                    1
            );

            Mockito.when(userService.getLoginUser()).thenReturn(mockUser);

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
        @DisplayName("AI 및 일반 피드백이 있을 때 GET 요청하면 OK 상태 코드와 해당 피드백 반환")
        void getFeedbackWithAIFeedback_Success() throws Exception {
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

            Mockito.when(feedbackService.getFeedbackByResumeId(resumeId)).thenReturn(List.of(feedback1, feedback2));
            Mockito.when(feedbackService.getAIFeedbackByResumeId(resumeId)).thenReturn(aiFeedback);

            // 실제 응답 예시:
            // "code":"피드백 조회 성공", "message":"FEEDBACK_200"
            mockMvc.perform(get("/api/v1/resumes/{resume_id}/feedbacks", resumeId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value("피드백 조회 성공"))
                    .andExpect(jsonPath("$.message").value("FEEDBACK_200"))
                    .andExpect(jsonPath("$.result.ai_feedback_content").value(aiFeedback.getFeedback()))
                    .andExpect(jsonPath("$.result.ai_feedback_id").value(
                            aiFeedback.getId())) // AI Feedback 저장 시 ID가 있을 것이라 가정
                    .andExpect(jsonPath("$.result.feedback_responses", hasSize(2)))
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
        @DisplayName("피드백이 없을 때 GET 요청하면 OK 상태 코드와 빈 리스트 반환")
        void getFeedbackWithAIFeedback_Empty() throws Exception {
            Long resumeId = 1L;
            Mockito.when(feedbackService.getFeedbackByResumeId(resumeId)).thenReturn(List.of());
            Mockito.when(feedbackService.getAIFeedbackByResumeId(resumeId)).thenReturn(null);

            mockMvc.perform(get("/api/v1/resumes/{resume_id}/feedbacks", resumeId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value("피드백 조회 성공"))
                    .andExpect(jsonPath("$.message").value("FEEDBACK_200"))
                    .andExpect(jsonPath("$.result.feedback_responses", hasSize(0)))
                    .andExpect(jsonPath("$.result.ai_feedback_content").doesNotExist())
                    .andExpect(jsonPath("$.result.ai_feedback_id").doesNotExist());
        }
    }

    @Nested
    @DisplayName("DELETE /api/v1/resumes/{resume_id}/feedbacks/{feedback_id}")
    class DeleteFeedback {

        @Test
        @DisplayName("삭제할 피드백이 있을 때 DELETE 요청하면 NO_CONTENT(204) 반환")
        void deleteFeedback_Success() throws Exception {
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

            mockMvc.perform(delete("/api/v1/resumes/{resume_id}/feedbacks/{feedback_id}", resumeId, feedbackId))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("존재하지 않는 피드백 삭제 요청시 BAD_REQUEST(400) 반환")
        void deleteFeedback_NotFound() throws Exception {
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

            // 실패 시에도 특정 code/message를 반환한다고 가정할 수 있지만,
            // 여기서는 단순히 BAD_REQUEST 상태 코드만 체크
            mockMvc.perform(delete("/api/v1/resumes/{resume_id}/feedbacks/{feedback_id}", resumeId, feedbackId))
                    .andExpect(status().isBadRequest());
        }
    }
}
