package com.techeer.backend.api.feedback.controller;

import com.techeer.backend.api.feedback.dto.Response.FeedbackListResponse;
import com.techeer.backend.api.feedback.service.FeedbackService;
import com.techeer.backend.api.feedback.dto.Request.FeedbackCreateRequest;
import com.techeer.backend.api.feedback.dto.Response.FeedbackResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "이력서 피드백 등록 API", description = "Feedback API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resumes/") // 공통 api
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Operation(summary = "피드백 등록", description = "원하는 위치에 피드백을 작성합니다.")
    @PostMapping("/{resume_id}/feedbacks")
    public ResponseEntity<FeedbackResponse> createFeedback(
            @PathVariable Long resume_id,
            @Valid @RequestBody FeedbackCreateRequest feedbackRequest) {

        log.info("이력서 ID: {} 에 대한 피드백 생성 요청", resume_id);

        // 피드백 생성 후 응답 생성
        FeedbackResponse feedbackResponse = feedbackService.createFeedback(
                resume_id,
                feedbackRequest.getContent(),
                feedbackRequest.getXCoordinate(),
                feedbackRequest.getYCoordinate()
        );

        log.info("피드백 생성 완료: {}", feedbackResponse);

        return new ResponseEntity<>(feedbackResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "피드백 리스트 조회", description = "특정 이력서의 피드백 전체를 리스트로 전달")
    @GetMapping("/{resume_id}/feedbacks")
    public ResponseEntity<FeedbackListResponse> getFeedback(@PathVariable Long resume_id) {
        FeedbackListResponse response = feedbackService.getFeedbackList(resume_id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "피드백 삭제")
    @DeleteMapping("/{resume_id}/feedbacks/{feedback_id}")
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable Long resume_id,
            @PathVariable Long feedback_id) {

        log.info("이력서 ID: {}, 피드백 ID: {} 에 대한 삭제 요청", resume_id, feedback_id);

        feedbackService.deleteFeedbackById(resume_id, feedback_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
