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

    public ErrorResponse(ErrorStatus errorStatus) {
        this.errorMessage = errorStatus.getMessage();
        this.httpStatus = errorStatus.getHttpStatus();
        this.code = errorStatus.getCode();
    }

    public ErrorResponse(ErrorStatus errorStatus, List<FieldError> fieldErrors) {
        this.httpStatus = errorStatus.getHttpStatus();
        this.code = errorStatus.getCode();
        this.errorMessage = errorStatus.getMessage();
        this.errors = fieldErrors;
    }

    public static ErrorResponse of(final ErrorStatus errorStatus, final BindingResult bindingResult) {
        return new ErrorResponse(errorStatus, FieldError.of(bindingResult));
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