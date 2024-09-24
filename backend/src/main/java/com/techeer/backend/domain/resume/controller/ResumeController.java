package com.techeer.backend.domain.resume.controller;

import com.techeer.backend.domain.resume.dto.request.CreateResumeReq;
import com.techeer.backend.domain.resume.service.ResumeService;
import com.techeer.backend.domain.user.entity.User;
import com.techeer.backend.domain.user.service.UserService;
import com.techeer.backend.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

//todo @RequiredArgsConstructor 추가 후 만든 생성자 삭제
@RestController
@RequiredArgsConstructor
@Tag(name = "resume", description = "Resume API")
@RequestMapping("/api/v1")
public class ResumeController {
    private final UserService userService;
    private final ResumeService resumeService;

    // 이력서 등록
    // todo
    @Operation(summary = "이력서 등록")
    @PostMapping(value="/resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> resumeRegistration(
            @RequestPart @Valid CreateResumeReq createResumeReq,
            @RequestPart(name = "resume_file") @Valid MultipartFile resumeFile) throws IOException {

        // 파일 유효성 검사 -> 나중에 vaildtor로 변경해서 유효성 검사할 예정
        if (resumeFile.isEmpty()) {
            throw new IllegalArgumentException("이력서 파일이 비어있습니다.");
        }

        if (!resumeFile.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("이력서 파일은 PDF 형식만 허용됩니다.");
        }

        // todo 유저 이름으로 객체 탐색
        Optional<User> registrars = userService.findUserByName(createResumeReq.getUsername());
        User registrar = null;
        if (registrars.isPresent()) {registrar = registrars.get();}

        // resume db에 저장
        resumeService.createResume(registrar, createResumeReq, resumeFile);


        return ResponseEntity.ok().build();
    }
}