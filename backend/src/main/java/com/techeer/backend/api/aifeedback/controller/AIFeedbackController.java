package com.techeer.backend.api.aifeedback.controller;

import com.techeer.backend.api.aifeedback.dto.AIFeedbackResponse;
import com.techeer.backend.api.aifeedback.service.AIFeedbackService;
import com.techeer.backend.api.feedback.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/aifeedbacks")
public class AIFeedbackController {
    private final AIFeedbackService aifeedbackService;

    public AIFeedbackController(AIFeedbackService aifeedbackService) {
        this.aifeedbackService = aifeedbackService;
    }

    @PostMapping("/{resumeId}")
    public ResponseEntity<?> createFeedbackFromS3(@PathVariable Long resumeId) {
        try {
            AIFeedbackResponse feedbackResponse = aifeedbackService.generateAIFeedbackFromS3(resumeId);
            return ResponseEntity.status(HttpStatus.CREATED).body(feedbackResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("피드백 생성 실패: " + e.getMessage());
        }
    }
}
