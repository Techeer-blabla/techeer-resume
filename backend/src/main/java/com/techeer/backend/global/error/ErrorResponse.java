package com.techeer.backend.global.error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
public class ErrorResponse {
    private HttpStatus httpStatus;
    private String code;
    private String errorMessage;
    private List<FieldError> errors = new ArrayList<>();


    public ErrorResponse(HttpStatus status, String s) {
        this.errorMessage = s;
        this.httpStatus = status;
    }

    public ErrorResponse(ErrorCode ErrorCode) {
        this.errorMessage = ErrorCode.getMessage();
        this.httpStatus = ErrorCode.getHttpStatus();
        this.code = ErrorCode.getCode();
    }

    public ErrorResponse(ErrorCode ErrorCode, List<FieldError> fieldErrors) {
        this.httpStatus = ErrorCode.getHttpStatus();
        this.code = ErrorCode.getCode();
        this.errorMessage = ErrorCode.getMessage();
        this.errors = fieldErrors;
    }

    public static ErrorResponse of(final ErrorCode ErrorCode, final BindingResult bindingResult) {
        return new ErrorResponse(ErrorCode, FieldError.of(bindingResult));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        public FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors =
                    bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(
                            error ->
                                    new FieldError(
                                            error.getField(),
                                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}