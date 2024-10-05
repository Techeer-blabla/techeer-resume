package com.techeer.backend.api.feedback.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// @ToString
public class FeedbackCreateRequest {

	@NotBlank(message = "content는 필수입니다.")
	private String content;

	@NotNull(message = "x 좌표는 필수입니다.")
	@JsonProperty("xCoordinate")
	private BigDecimal xCoordinate;

	@NotNull(message = "y 좌표는 필수입니다.")
	@JsonProperty("yCoordinate")
	private BigDecimal yCoordinate;

	// Swagger UI에서 기본값을 설정할 수 있도록 하기 위해 추가
	/*좀 후진 방법인거 같긴한데 일단 이걸로 해야할듯*/
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private BigDecimal xcoordinate = null;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private BigDecimal ycoordinate = null;
}
