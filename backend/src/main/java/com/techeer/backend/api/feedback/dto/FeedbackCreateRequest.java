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
	@JsonProperty("x_coordinate")
	private BigDecimal xCoordinate;

	@NotNull(message = "y 좌표는 필수입니다.")
	@JsonProperty("y_coordinate")
	private BigDecimal yCoordinate;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private BigDecimal xcoordinate = null;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private BigDecimal ycoordinate = null;
}
