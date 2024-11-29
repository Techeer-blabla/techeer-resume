package com.techeer.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.dto.request.FeedbackCreateRequest;
import com.techeer.backend.api.feedback.repository.FeedbackRepository;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.domain.ResumePdf;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.tag.position.Position;
import com.techeer.backend.api.user.domain.User;
import com.techeer.backend.api.user.repository.UserRepository;
import com.techeer.backend.global.vo.Pdf;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc(addFilters = false)
public class FeedbackIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Resume testResume;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Set SecurityContext with Mock Authentication
        SecurityContextHolder.setContext(new SecurityContextImpl(
                new UsernamePasswordAuthenticationToken(
                        testUser, null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
                )
        ));

        // User 저장
        userRepository.save(testUser);

        // Resume 저장
        Pdf testPdf = new Pdf("http://example.com/pdf", "resume.pdf", "unique-id-123");
        testResume = Resume.builder()
                .user(testUser)
                .name("ggg")
                .career(2)
                .position(Position.BACKEND)
                .resumePdf(ResumePdf.builder().pdf(testPdf).build())
                .build();
        resumeRepository.save(testResume);
    }


    @Test
    @DisplayName("Feedback 생성 요청 통합 테스트")
    void createFeedbackIntegrationTest() throws Exception {
        Long resumeId = testResume.getId();
        FeedbackCreateRequest request = new FeedbackCreateRequest(
                "This is a test feedback", 100.5, 200.0, 200.0, 300.0, 1);

        String requestBody = objectMapper.writeValueAsString(request);

        try {
            mockMvc.perform(post("/api/v1/resumes/{resume_id}/feedbacks", resumeId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.data.content").value("This is a test feedback"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Feedback> savedFeedbacks = feedbackRepository.findAllByResumeId(resumeId);
        assertFalse(savedFeedbacks.isEmpty(), "Feedback should be saved in the database");
        assertEquals(1, savedFeedbacks.size());
        assertEquals("This is a test feedback", savedFeedbacks.get(0).getContent());
        assertEquals(resumeId, savedFeedbacks.get(0).getResume().getId());
    }

}
