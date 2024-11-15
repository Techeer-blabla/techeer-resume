package com.techeer.backend.global.error;

import com.techeer.backend.global.common.response.ReasonDto;
import com.techeer.backend.global.common.status.BaseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseStatus {
    // Common Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 에러입니다. 관리자에게 문의하세요."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "금지된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_404", "찾을 수 없는 요청입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON_405", "허용되지 않은 메소드입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "잘못된 요청입니다."),


    // Resume Error
    RESUME_FILE_EMPTY(HttpStatus.BAD_REQUEST, "RESUME_400", "이력서 파일이 비어있습니다"),
    RESUME_FILE_TYPE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "RESUME_TYPE_400", "이력서 파일은 PDF 형식만 허용됩니다."),
    RESUME_NOT_FOUND(HttpStatus.NOT_FOUND, "RESUME_404", "이력서를 찾을 수 없습니다"),
    RESUME_PDF_NOT_FOUND(HttpStatus.NOT_FOUND, "RESUME_PDF_404", "이력서 pdf를 찾을 수 없습니다"),
    RESUME_UPLOAD_ERROR(HttpStatus.BAD_REQUEST, "RESUME_PDf_502", "S3에 pdf를 올리는 것에 실패했습니다"),

    OPENAI_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "OPENAI_502", "OPENAI 서버에 연결하는 데 실패했습니다"),

    // Feedback Error
    FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "FEEDBACK_404", "피드백을 찾을 수 없습니다."),
    INVALID_FEEDBACK_FOR_RESUME(HttpStatus.BAD_REQUEST, "FEEDBACK_400", "이력서에 해당하는 피드백이 아닙니다."),

    // AIFeedback Error
    AIFEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "AIFEEDBACK_404", "AI 피드백을 찾을 수 없습니다."),
    ;

    // User Error

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .status(httpStatus)
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }
}
