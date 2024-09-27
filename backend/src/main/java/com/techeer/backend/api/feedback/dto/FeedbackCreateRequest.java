package com.techeer.backend.api.feedback.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@ToString
public class FeedbackCreateRequest {

    @NotBlank(message = "content는 필수입니다.")
    private String content;

    @NotNull(message = "x 좌표는 필수입니다.")
    @JsonProperty("xCoordinate")
    private BigDecimal xCoordinate;

    @NotNull(message = "y 좌표는 필수입니다.")
    @JsonProperty("yCoordinate")
    private BigDecimal yCoordinate;
}
