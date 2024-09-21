package com.techeer.backend.domain.resume.service;

import com.techeer.backend.domain.resume.dto.request.CreateResumeReq;
import com.techeer.backend.domain.resume.entity.Resume;
import com.techeer.backend.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeService {

    //todo 이력서 데이터 베이스에 저장
    public void createResume(User user, CreateResumeReq req, MultipartFile resume_pdf) throws IOException {

        Resume resume = req.toResume(user, req);

        String pdfName = resume_pdf.getOriginalFilename();
        // 주의 이름
        String s3PdfName = "resume/" + UUID.randomUUID().toString().substring(0, 10) + "_" + pdfName;

    }
}
