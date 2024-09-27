package com.techeer.backend.api.resume.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.techeer.backend.api.resume.dto.request.CreateResumeRequest;
import com.techeer.backend.api.resume.dto.request.ResumeSearchRequest;
import com.techeer.backend.api.resume.dto.response.ResumeResponse;
import com.techeer.backend.api.resume.domain.Resume;
import com.techeer.backend.api.resume.repository.GetResumeRepository;
import com.techeer.backend.api.resume.repository.ResumeRepository;
import com.techeer.backend.api.resume.repository.ResumeSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeService {
    private final AmazonS3 amazonS3;
    private final ResumeRepository resumeRepository;
    private final GetResumeRepository getResumeRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    //todo 이력서 데이터 베이스에 저장
    //todo dto 변경
    @Transactional
    public void createResume(CreateResumeRequest req, MultipartFile resume_pdf) throws IOException {

        Resume resume = req.toEntity();

        String pdfName = resume_pdf.getOriginalFilename();
        // todo 중복된 이름이 걸려서 덮어 씌어질 수 있다.
        String s3PdfName = UUID.randomUUID().toString().substring(0, 10) + "_" + pdfName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(resume_pdf.getSize());
        metadata.setContentType(resume_pdf.getContentType());

        amazonS3.putObject(bucket, "resume/" + s3PdfName, resume_pdf.getInputStream(), metadata);
        String resumeUrl = amazonS3.getUrl(bucket, "resume/" +s3PdfName).toString();
        resume.updateUrl(resumeUrl);

        resumeRepository.save(resume);
    }

    // 유저 이름으로 이력서 찾기
    public List<ResumeResponse> searchResumesByUserName(String userName) {
        List<Resume> resumes = resumeRepository.findByUsername(userName);
        return resumes.stream()
                .map(resume -> new ResumeResponse(resume.getId(), resume.getUsername(), resume.getResumeName(), resume.getUrl()))
                .toList();
    }

    // 태그 조회
    @Transactional(readOnly = true)
    public List<ResumeResponse> searchByTages(ResumeSearchRequest req, Pageable pageable) {
        Specification<Resume> spec = ResumeSpecification.search(req);
        Page<Resume> allActiveResumes = getResumeRepository.findAllActiveResumes(spec, pageable);
        return allActiveResumes.stream()
                .map(ResumeResponse::from)
                .collect(Collectors.toList());
    }

    public ResumePageResponse getResumePage(Pageable pageable) {
        Page<Resume> resumes = resumeRepository.findByResumePage(pageable);
        List<ResumePageElement> elements = resumes.getContent().stream()
                .map(ResumePageElement::of)
                .toList();

        ResumePageResponse result = ResumePageResponse.from(elements, resumes);

        return result;
    }
}
