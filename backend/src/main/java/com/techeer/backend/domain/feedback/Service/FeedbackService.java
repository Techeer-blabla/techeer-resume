package com.techeer.backend.domain.feedback.Service;

import com.techeer.backend.domain.feedback.Repository.FeedbackRepository;

import com.techeer.backend.domain.feedback.dto.FeedbackResponse;
import com.techeer.backend.domain.feedback.entity.Feedback;

import com.techeer.backend.domain.resume.entity.Resume;
import com.techeer.backend.domain.resume.repository.ResumeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedbackService {

    private static final String RESUME_NOT_FOUND_MESSAGE = "이력서를 찾을 수 없습니다.";
    private static final String FEEDBACK_NOT_FOUND_MESSAGE = "피드백을 찾을 수 없습니다.";
    private static final String INVALID_FEEDBACK_FOR_RESUME_MESSAGE = "이력서에 해당하는 피드백이 아닙니다";

    private final FeedbackRepository feedbackRepository;
    private final ResumeRepository resumeRepository;

    @Transactional
    public FeedbackResponse createFeedback(Long resumeId, String content, BigDecimal xCoordinate, BigDecimal yCoordinate) {
        Resume resume = getResumeById(resumeId);
        log.info("이력서 ID: {} 에 피드백 생성 중", resumeId);

        Feedback feedback = Feedback.builder()
                .resume(resume)
                .content(content)
                .xCoordinate(xCoordinate)
                .yCoordinate(yCoordinate)
                .build();

        Feedback savedFeedback = feedbackRepository.save(feedback);

        log.info("피드백 생성 완료: {}", savedFeedback);

        return toFeedbackResponse(savedFeedback);
    }

    @Transactional
    public void deleteFeedbackById(Long resumeId, Long feedbackId) {

        // 이력서가 존재하는지 먼저 확인
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new EntityNotFoundException(RESUME_NOT_FOUND_MESSAGE));

        // 피드백이 존재하고, 해당 이력서와 연관되어 있는지 확인
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new IllegalArgumentException(FEEDBACK_NOT_FOUND_MESSAGE));

        // 피드백이 이력서와 연관되어 있는지 확인
        if (!feedback.getResume().getId().equals(resume.getId())) {
            throw new IllegalArgumentException(String.format(INVALID_FEEDBACK_FOR_RESUME_MESSAGE));
        }

        log.info("피드백 삭제 중: 피드백 ID {} (이력서 ID {})", feedbackId, resumeId);

        // 피드백 삭제
        feedbackRepository.delete(feedback);
    }



    private Resume getResumeById(Long resumeId) {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() -> new IllegalArgumentException(RESUME_NOT_FOUND_MESSAGE));
    }

    private FeedbackResponse toFeedbackResponse(Feedback feedback) {
        return FeedbackResponse.of(
                feedback.getId(),
                feedback.getResume().getId(),
                feedback.getContent(),
                feedback.getXCoordinate(),
                feedback.getYCoordinate()
        );
    }
}
