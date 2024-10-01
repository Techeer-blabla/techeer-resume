package com.techeer.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // global
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G001", "서버 오류"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "G002", "오브젝트를 찾을 수 없다."),

    // Resume
    RESUME_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "이력서를 찾을 수 없음"),
    RESUME_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "R002", "이력서 업로드 실패"),
    RESUME_S3_DOWNLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "R003", "S3에서 이력서 다운로드 실패"),
    RESUME_PDF_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "R004", "PDF 파일 변환 실패"),

    // OpenAI
    OPENAI_CLIENT_ERROR(HttpStatus.BAD_REQUEST, "O001", "OpenAI 클라이언트 오류"),
    OPENAI_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "O002", "OpenAI 서버 오류"),
    OPENAI_INVALID_RESPONSE(HttpStatus.BAD_GATEWAY, "O003", "OpenAI 응답 오류"),
    ;

    private HttpStatus status;
    private String code;
    private String message;
}
