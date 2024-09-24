package com.techeer.backend.domain.resume.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.techeer.backend.domain.resume.dto.request.CreateResumeReq;
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
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeService {
    private final AmazonS3 amazonS3;
    private final ResumeRepository resumeRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    //todo 이력서 데이터 베이스에 저장
    //todo dto 변경
    @Transactional
    public void createResume(User user, CreateResumeReq dto, MultipartFile resume_pdf) throws IOException {

        Resume resume = dto.toEntity(user, dto);

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
}
