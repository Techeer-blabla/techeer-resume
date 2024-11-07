package com.techeer.backend.api.resume.controller;

import com.techeer.backend.api.resume.dto.request.CreateResumeRequest;
import com.techeer.backend.api.resume.dto.request.ResumeSearchRequest;
import com.techeer.backend.api.resume.dto.response.PageableResumeResponse;
import com.techeer.backend.api.resume.dto.response.ResumeDetailResponse;
import com.techeer.backend.api.resume.dto.response.ResumeResponse;
import com.techeer.backend.api.resume.service.ResumeService;
import com.techeer.backend.api.resume.service.facade.ResumeCreateFacade;
import com.techeer.backend.global.error.ErrorStatus;
import com.techeer.backend.global.error.exception.GeneralException;
import com.techeer.backend.global.success.SuccessCode;
import com.techeer.backend.global.success.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//todo @RequiredArgsConstructor 추가 후 만든 생성자 삭제
@RestController
@RequiredArgsConstructor
@Tag(name = "resume", description = "Resume API")
@RequestMapping("/api/v1")
public class ResumeController {
    private final ResumeCreateFacade resumeCreateFacade;
    private final ResumeService resumeService;

    // 이력서 등록
    // todo
    @Operation(summary = "이력서 등록")
    @PostMapping(value = "/resumes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> resumeRegistration(@RequestPart @Valid CreateResumeRequest createResumeReq,
                                                              @RequestPart(name = "resume_file")
                                                              @Valid MultipartFile resumeFile) {
        // 파일 유효성 검사 -> 나중에 vaildtor로 변경해서 유효성 검사할 예정
        if (resumeFile.isEmpty()) {
            throw new GeneralException(ErrorStatus.RESUME_FILE_EMPTY);
        }
        if (!resumeFile.getContentType().equals("application/pdf")) {
            throw new GeneralException(ErrorStatus.RESUME_FILE_TYPE_NOT_ALLOWED);
        }
        // todo 유저 이름으로 객체 탐색
        //        Optional<User> registrars = userService.findUserByName(createResumeReq.getUsername());
        //        User registrar = null;
        //        if (registrars.isPresent()) {registrar = registrars.get();}

        resumeCreateFacade.createResume(createResumeReq, resumeFile);
        return ResponseEntity.ok(SuccessResponse.of(SuccessCode.SUCCESS));
    }


    @Operation(summary = "회원 이름으로 이력서 조회")
    @GetMapping("/resumes/search")
    public ResponseEntity<List<ResumeResponse>> searchResumesByUserName(@RequestParam("user_name") String userName) {
        List<ResumeResponse> resumeResponse = resumeService.searchResumesByUserName(userName);
        return ResponseEntity.ok(resumeResponse);
    }


    @Operation(summary = "이력서 개별 조회")
    @GetMapping("/resumes/{resume_id}")
    public ResponseEntity<SuccessResponse> searchResumeDetail(@PathVariable("resume_id") Long resumeId) {

        ResumeDetailResponse resumeContent = resumeService.getResumeContent(resumeId);
        SuccessResponse response = SuccessResponse.of(SuccessCode.SUCCESS, resumeContent);

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "여러 이력서 조회(페이지네이션)")
    @GetMapping(value = "/resumes")
    public ResponseEntity<List<PageableResumeResponse>> searchResumes(@RequestParam int page,
                                                                      @RequestParam int size) {
        //ResumeService를 통해 페이지네이션된 이력서 목록을 가져옵니다.
        final Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        final List<PageableResumeResponse> pageableResumeResponses = resumeService.getResumePage(pageable);

        if (pageableResumeResponses == null) {
            // 404 Not Found
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageableResumeResponses);
    }


    @Operation(summary = "이력서 태그 조회")
    @PostMapping("/resumes/search")
    public ResponseEntity<List<PageableResumeResponse>> searchResumesByTag(@RequestParam int page,
                                                                           @RequestParam int size,
                                                                           @RequestBody ResumeSearchRequest dto) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        final List<PageableResumeResponse> pageableResumeRespons = resumeService.searchByTages(dto, pageable);
        return ResponseEntity.ok(pageableResumeRespons);
    }
}
