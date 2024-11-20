package com.techeer.backend.api.feedback.service;

import com.techeer.backend.api.aifeedback.domain.AIFeedback;
import com.techeer.backend.api.aifeedback.repository.AIFeedbackRepository;
import com.techeer.backend.api.feedback.converter.FeedbackConverter;
import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.dto.AllFeedbackResponse;
import com.techeer.backend.api.feedback.dto.FeedbackCreateRequest;
import com.techeer.backend.api.feedback.dto.FeedbackResponse;
import com.techeer.backend.api.feedback.repository.FeedbackRepository;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.global.error.ErrorStatus;
import com.techeer.backend.global.error.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ResumeRepository resumeRepository;
    private final AIFeedbackRepository aiFeedbackRepository;

    @Transactional
    public FeedbackResponse createFeedback(Long resumeId, FeedbackCreateRequest feedbackCreateRequest) {
        Resume resume = resumeRepository.findByIdAndDeletedAtIsNull(resumeId)
                .orElseThrow(() -> new BusinessException(ErrorStatus.RESUME_NOT_FOUND));

        Feedback feedback = Feedback.of(resume, feedbackCreateRequest);
        feedbackRepository.save(feedback);

        return FeedbackConverter.toFeedbackResponse(feedback);
    }

    @Transactional
    public void deleteFeedbackById(Long resumeId, Long feedbackId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new BusinessException(ErrorStatus.RESUME_NOT_FOUND));

        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new BusinessException(ErrorStatus.FEEDBACK_NOT_FOUND));

        if (!feedback.getResume().getId().equals(resume.getId())) {
            throw new BusinessException(ErrorStatus.INVALID_FEEDBACK_FOR_RESUME);
        }

        log.info("피드백 삭제 중: 피드백 ID {} (이력서 ID {})", feedbackId, resumeId);

        feedbackRepository.delete(feedback);
    }

    @Transactional(readOnly = true)
    public AllFeedbackResponse getFeedbackWithAIFeedback(Long resumeId) {
        // resumeId에 해당하는 모든 피드백 가져오기
        List<Feedback> feedbacks = feedbackRepository.findAllByResumeId(resumeId);
        if (feedbacks.isEmpty()) {
            throw new BusinessException(ErrorStatus.FEEDBACK_NOT_FOUND);
        }

        // resumeId에 해당하는 AI 피드백 가져오기 (없으면 null 설정)
        AIFeedback aiFeedback = aiFeedbackRepository.findByResumeId(resumeId).orElse(null);

        // FeedbackConverter에서 모든 피드백 리스트와 AI 피드백을 포함한 응답으로 변환
        return FeedbackConverter.toAllFeedbackResponse(feedbacks, aiFeedback);
    }

}
