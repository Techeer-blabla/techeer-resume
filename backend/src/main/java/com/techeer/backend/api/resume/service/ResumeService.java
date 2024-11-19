package com.techeer.backend.api.resume.service;

import com.techeer.backend.api.feedback.domain.Feedback;
import com.techeer.backend.api.feedback.repository.FeedbackRepository;
import com.techeer.backend.api.resume.converter.ResumeConverter;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.dto.request.ResumeSearchRequest;
import com.techeer.backend.api.resume.dto.response.ResumeDetailResponse;
import com.techeer.backend.api.resume.repository.GetResumeRepository;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.global.error.exception.NotFoundException;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final GetResumeRepository getResumeRepository;
    private final FeedbackRepository feedbackRepository;

    public Resume saveResume(Resume resume) {
        Resume savedResume = resumeRepository.save(resume);
        return savedResume;
    }

    //todo 피드백까지 생기면
    // 이력서 개별 조회
    @Transactional(readOnly = true)
    public ResumeDetailResponse getResumeContent(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(NotFoundException::new);

        // 이력서의 피드백 찾기
        List<Feedback> feedbacks = feedbackRepository.findAllByResumeId(resumeId);

        // ResumeDetailResponse 객체 생성 후 반환
        return ResumeConverter.toResumeDetailResponse(resume, feedbacks);
    }

    // 유저 이름으로 이력서 조회
    public List<Resume> searchResumesByUserName(String userName) {
        return resumeRepository.findByUsername(userName);
    }

    // 태그 조회
    public List<Resume> searchByTages(ResumeSearchRequest req, Pageable pageable) {
        List<String> techStackNames = req.getTechStackNames();
        List<String> companyNames = req.getCompanyNames();

        // 빈 리스트를 null로 설정
        if (techStackNames == null || techStackNames.isEmpty()) {
            techStackNames = null;
        }
        if (companyNames == null || companyNames.isEmpty()) {
            companyNames = null;
        }

        Page<Resume> resumesByCriteria = getResumeRepository.findResumesByCriteria(
                req.getMinCareer(),
                req.getMaxCareer(),
                techStackNames,
                companyNames,
                pageable
        );

        return (List<Resume>) resumesByCriteria;
    }


    public List<Resume> getResumePage(Pageable pageable) {
        // 페이지네이션을 적용하여 레포지토리에서 데이터를 가져옴
        Page<Resume> resumes = resumeRepository.findAll(pageable);

        // 첫 번째 Resume 객체를 가져옴 (예시로 첫 번째 요소를 변환)
        // 여러 Resume 객체를 페이지로 처리하려면 추가 로직 필요
        Resume resume = resumes.getContent().isEmpty() ? null : resumes.getContent().get(0);

        // Resume가 없을 경우 빈 결과를 처리
        if (resume == null) {
            return null;
        }

        return (List<Resume>) resumes;
    }
}
