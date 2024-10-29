package com.techeer.backend.api.resume.dto.request;

import java.util.List;

import com.techeer.backend.api.resume.domain.Position;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

//todo Getter 한번만 사용하기
@Getter
@Builder
public class CreateResumeRequest {
	// 나중에 로그인 생기면 수정해야 된다.

	@NotBlank(message = "content는 필수입니다.")
	private String username;

	@NotNull(message = "content는 필수입니다.")
	private Position position;

	@NotNull(message = "content는 필수입니다.")
	private int career;

	private List<String> applyingCompanies;

	private List<String> techStacks;

}

