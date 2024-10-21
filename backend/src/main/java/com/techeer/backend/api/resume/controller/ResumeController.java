package com.techeer.backend.api.resume.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.techeer.backend.api.resume.dto.request.CreateResumeRequest;
import com.techeer.backend.api.resume.dto.request.ResumeSearchRequest;
import com.techeer.backend.api.resume.dto.response.FetchResumeContentResponse;
import com.techeer.backend.api.resume.dto.response.ResumePageResponse;
import com.techeer.backend.api.resume.dto.response.ResumeResponse;
import com.techeer.backend.api.resume.service.ResumeService;
import com.techeer.backend.global.success.SuccessCode;
import com.techeer.backend.global.success.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//todo @RequiredArgsConstructor 추가 후 만든 생성자 삭제
@RestController
@RequiredArgsConstructor
@Tag(name = "resume", description = "Resume API")
@RequestMapping("/api/v1")
public class ResumeController {
	private final ResumeService resumeService;

	// 이력서 등록
	// todo
	@Operation(summary = "이력서 등록")
	@PostMapping(value = "/resumes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<SuccessResponse> resumeRegistration(
		@RequestPart @Valid CreateResumeRequest createResumeReq,
		@RequestPart(name = "resume_file") @Valid MultipartFile resumeFile) throws IOException {
		// 파일 유효성 검사 -> 나중에 vaildtor로 변경해서 유효성 검사할 예정
		if (resumeFile.isEmpty()) {
			throw new IllegalArgumentException("이력서 파일이 비어있습니다.");
		}
		if (!resumeFile.getContentType().equals("application/pdf")) {
			throw new IllegalArgumentException("이력서 파일은 PDF 형식만 허용됩니다.");
		}
		// todo 유저 이름으로 객체 탐색
		//        Optional<User> registrars = userService.findUserByName(createResumeReq.getUsername());
		//        User registrar = null;
		//        if (registrars.isPresent()) {registrar = registrars.get();}

		// resume db에 저장
		resumeService.createResume(createResumeReq, resumeFile);
		return ResponseEntity.ok().build();
	}

	// 회원 이름으로 게시물 조회
	@Operation(summary = "회원 이름으로 이력서 조회")
	@GetMapping("/resumes/search")
	public ResponseEntity<List<ResumeResponse>> searchResumesByUserName(@RequestParam("user_name") String userName) {
		List<ResumeResponse> resumeResponses = resumeService.searchResumesByUserName(userName);
		return ResponseEntity.ok(resumeResponses);
	}

	//todo 피드백 완성되면 작업
	@Operation(summary = "이력서 개별 조회")
	@GetMapping("/resumes/{resume_id}")
	public ResponseEntity<SuccessResponse> fetchResumeContent(@PathVariable("resume_id") Long resumeId) throws IOException {

		FetchResumeContentResponse resumeContent = resumeService.getResumeContent(resumeId);
		SuccessResponse response = SuccessResponse.of(SuccessCode.SUCCESS, resumeContent);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "이력서 여러 조회")
	@GetMapping(value = "/resumes")
	public ResponseEntity<List<ResumePageResponse>> getResumes(
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		//ResumeService를 통해 페이지네이션된 이력서 목록을 가져옵니다.
		List<ResumePageResponse> resumes = resumeService.getResumePage(pageable);

		if (resumes == null) {
			// 404 Not Found
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(resumes);
	}

	// 태그 조회 (페이지네이션)
	@Operation(summary = "이력서 태그 조회")
	@PostMapping("/resumes/search")
	public ResponseEntity<List<ResumeResponse>> getResumesByTag(@RequestBody ResumeSearchRequest dto,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {
		List<ResumeResponse> resumeResponses = resumeService.searchByTages(dto, pageable);
		return ResponseEntity.ok(resumeResponses);
	}
}
