package com.techeer.backend.feedback.controller;

import com.techeer.backend.feedback.Service.FeedbackService;
import com.techeer.backend.feedback.dto.FeedbackRequest;
import com.techeer.backend.feedback.dto.FeedbackResponse;
import com.techeer.backend.feedback.entity.Feedback;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="이력서 피드백 등록 API")
@RestController
@RequestMapping("/resumes/{resumeId}/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @PostMapping
    public ResponseEntity<FeedbackResponse> createFeedback(
           @PathVariable Long resumeId,
           @RequestBody FeedbackRequest feedbackRequest){
        Feedback feedback = feedbackService.createFeedback(
                resumeId,
                feedbackRequest.getContent(),
                feedbackRequest.getxCoordinate(),
                feedbackRequest.getyCoordinate()
        );

        FeedbackResponse feedbackResponse = new FeedbackResponse (
                feedback.getId(),
                feedback.getResume().getId(),
                feedback.getContent(),
                feedback.getxCoordinate(),
                feedback.getyCoordinate()
        );

        return new ResponseEntity<>(feedbackResponse, HttpStatus.CREATED);
    }
}
