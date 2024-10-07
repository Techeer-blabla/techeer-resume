package com.techeer.backend.api.feedback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techeer.backend.api.feedback.dto.FeedbackCreateRequest;
import com.techeer.backend.api.feedback.dto.FeedbackResponse;
import com.techeer.backend.api.feedback.service.FeedbackService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
		@PathVariable("resume_id") Long resumeId,
		@Valid @RequestBody FeedbackCreateRequest feedbackRequest) {

		log.info("이력서 ID: {} 에 대한 피드백 생성 요청", resumeId);

		// 피드백 생성 후 응답 생성
		FeedbackResponse feedbackResponse = feedbackService.createFeedback(resumeId, feedbackRequest);

		log.info("피드백 생성 완료: {}", feedbackResponse);

		return new ResponseEntity<>(feedbackResponse, HttpStatus.CREATED);
	}

	@Operation(summary = "피드백 삭제")
	@DeleteMapping("/{resume_id}/feedbacks/{feedback_id}")
	public ResponseEntity<Void> deleteFeedback(
		@PathVariable("resume_id") Long resumeId,
		@PathVariable("feedback_id") Long feedbackId) {

		log.info("이력서 ID: {}, 피드백 ID: {} 에 대한 삭제 요청", resumeId, feedbackId);

		feedbackService.deleteFeedbackById(resumeId, feedbackId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
