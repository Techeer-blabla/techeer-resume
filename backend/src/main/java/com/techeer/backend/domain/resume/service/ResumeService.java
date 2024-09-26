package com.techeer.backend.domain.resume.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.techeer.backend.domain.resume.dto.request.CreateResumeReq;
import com.techeer.backend.domain.resume.dto.response.FetchResumeContentRes;
import com.techeer.backend.domain.resume.dto.response.ResumeResponse;
import com.techeer.backend.domain.resume.entity.Resume;
import com.techeer.backend.domain.resume.repository.ResumeRepository;
import com.techeer.backend.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeService {
    private final AmazonS3 amazonS3;
    private final ResumeRepository resumeRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void createResume(User user, CreateResumeReq dto, MultipartFile resumePdf) throws IOException {

        Resume resume = dto.toEntity(user, dto);

        String pdfName = resumePdf.getOriginalFilename();
        String s3PdfName = UUID.randomUUID().toString().substring(0, 10) + "_" + pdfName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(resumePdf.getSize());
        metadata.setContentType(resumePdf.getContentType());

        amazonS3.putObject(bucket, "resume/" + s3PdfName, resumePdf.getInputStream(), metadata);
        String resumeUrl = amazonS3.getUrl(bucket, "resume/" +s3PdfName).toString();
        resume.updateUrl(resumeUrl);

        resumeRepository.save(resume);
    }

    //todo 피드백까지 생기면
    @Transactional(readOnly = true)
    public FetchResumeContentRes getResumeContent(Long resumeId){
        Optional<Resume> foundResume = resumeRepository.findById(resumeId);

        return null;
    }

    public List<ResumeResponse> searchResumesByUserName(String userName) {
        List<Resume> resumes = resumeRepository.findByUserUsername(userName);
        return resumes.stream()
                .map(resume -> new ResumeResponse(resume.getId(), resume.getUser().getUsername(), resume.getResumeName(), resume.getUrl()))
                .toList();
    }
    // 태그 타입과 태그 값을 기준으로 이력서 조회
    public List<Resume> getResumesByTag(Resume.TagType tagType, Enum<?> tag) {
        if (tagType == Resume.TagType.POSITION) {
            return resumeRepository.findByPosition((Resume.Position) tag);
        } else if (tagType == Resume.TagType.TECH_STACK) {
            return resumeRepository.findByTechStack((Resume.TechStack) tag);
        } else {
            throw new IllegalArgumentException("Invalid tag type");
        }
    }

}
