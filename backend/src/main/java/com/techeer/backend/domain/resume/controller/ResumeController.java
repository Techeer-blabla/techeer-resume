package com.techeer.backend.domain.resume.controller;

import com.techeer.backend.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User", description = "User API")
@RequestMapping("/api/v1")
public class ResumeController {

    // 이력서 등록
    @Operation(summary = "이력서 생성")
    @PostMapping("/resume")
    public ResponseEntity<SuccessResponse> resumeRegistration(){
        return null;
    }
}
