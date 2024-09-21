package com.techeer.backend.domain.resume.controller;

import com.techeer.backend.domain.resume.dto.request.CreateResumeReq;
import com.techeer.backend.domain.resume.service.ResumeService;
import com.techeer.backend.domain.user.entity.User;
import com.techeer.backend.domain.user.service.UserService;
import com.techeer.backend.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Tag(name = "resume", description = "Resume API")
@RequestMapping("/api/v1")
public class ResumeController {
    private UserService userService;
    private ResumeService resumeService;

    @Autowired
    public ResumeController(UserService userService, ResumeService resumeService) {
        this.userService = userService;
        this.resumeService = resumeService;
    }

    // 이력서 등록
    @Operation(summary = "이력서 등록")
    @PostMapping("/resume")
    public ResponseEntity<SuccessResponse> resumeRegistration(
            @RequestPart @Validated CreateResumeReq createResumeReq,
            @RequestPart(name = "resume_file") MultipartFile resumeFile) throws IOException {


        // 파일 유효성 검사 -> 나중에 vaildtor로 변경해서 유효성 검사할 예정
        if (resumeFile.isEmpty()) {
            throw new IllegalArgumentException("이력서 파일이 비어있습니다.");
        }

        if (!resumeFile.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("이력서 파일은 PDF 형식만 허용됩니다.");
        }
        /*
        if (resumeFile.getSize() > 5 * 1024 * 1024) {  // 예: 5MB 이하 제한
            throw new IllegalArgumentException("이력서 파일 크기는 5MB 이하로 제한됩니다.");
        }
        */

        // 유저 이름으로 객체 탐색
        User registrar = userService.findUserByName(createResumeReq.getUsername());

        // resume db에 저장
        try {
            resumeService.createResume(registrar, createResumeReq, resumeFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().build();
    }
}
