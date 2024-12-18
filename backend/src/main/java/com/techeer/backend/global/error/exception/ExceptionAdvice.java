package com.techeer.backend.global.error.exception;


import com.techeer.backend.global.common.response.CommonResponse;
import com.techeer.backend.global.common.response.ReasonDto;
import com.techeer.backend.global.error.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "ConstraintViolationException이 ConstraintViolation을 포함하지 않습니다."));

        ErrorCode ErrorCode = ErrorCode.BAD_REQUEST;
        for (ErrorCode value : ErrorCode.values()) {
            if (value.name().equals(errorMessage)) {
                ErrorCode = value;
                break;
            }
        }

        return handleExceptionInternalConstraint(e, ErrorCode, HttpHeaders.EMPTY, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage,
                            (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        return handleExceptionInternalArgs(e, HttpHeaders.EMPTY, ErrorCode.BAD_REQUEST, request, errors);
    }

    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity<Object> handleGeneral(GeneralException generalException, HttpServletRequest request) {
        ReasonDto errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();

        return handleExceptionInternalGeneral(generalException, errorReasonHttpStatus, null, request);
    }

    @ExceptionHandler(InvalidPropertyException.class)
    protected ResponseEntity<?> handleInvalidPropertyException(InvalidPropertyException e,
                                                               WebRequest request) {

        ErrorCode ErrorCode = ErrorCode.BAD_REQUEST;
        return handleExceptionInternalConstraint(e, ErrorCode, HttpHeaders.EMPTY, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleOthers(Exception e, WebRequest request) {
        return handleExceptionInternalOthers(e, ErrorCode.INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY,
                ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus(), request, e.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorCode ErrorCode,
                                                                     HttpHeaders headers, WebRequest request) {
        CommonResponse<Object> body = CommonResponse.onFailure(ErrorCode.getHttpStatus(),
                ErrorCode.getCode(),
                ErrorCode.getMessage(),
                null);

        return super.handleExceptionInternal(e, body, headers, ErrorCode.getHttpStatus(), request);
    }

    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e, HttpHeaders headers,
                                                               ErrorCode ErrorCode,
                                                               WebRequest request,
                                                               Map<String, String> errorArgs) {
        CommonResponse<Object> body = CommonResponse.onFailure(ErrorCode.getHttpStatus(),
                ErrorCode.getCode(),
                ErrorCode.getMessage(),
                errorArgs);

        return super.handleExceptionInternal(e, body, headers, ErrorCode.getHttpStatus(), request);
    }

    private ResponseEntity<Object> handleExceptionInternalGeneral(Exception e,
                                                                  ReasonDto reason,
                                                                  HttpHeaders headers,
                                                                  HttpServletRequest request) {
        CommonResponse<Object> body = CommonResponse.onFailure(reason.getStatus(),
                reason.getCode(),
                reason.getMessage(),
                null);

        return super.handleExceptionInternal(e, body, headers, reason.getStatus(), new ServletWebRequest(request));
    }

    private ResponseEntity<Object> handleExceptionInternalOthers(Exception e, ErrorCode ErrorCode,
                                                                 HttpHeaders headers, HttpStatus status,
                                                                 WebRequest request, String errorPoint) {
        CommonResponse<Object> body = CommonResponse.onFailure(ErrorCode.getReasonHttpStatus().getStatus(),
                ErrorCode.getCode(),
                ErrorCode.getMessage(),
                errorPoint);

        return super.handleExceptionInternal(e, body, headers, status, request);
    }
}
