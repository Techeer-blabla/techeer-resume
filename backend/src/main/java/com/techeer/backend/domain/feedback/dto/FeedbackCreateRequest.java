package com.techeer.backend.domain.feedback.dto;

import jakarta.validation.constraints.NotBlank;
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

    private BigDecimal xCoordinate;

    private BigDecimal yCoordinate;
}
