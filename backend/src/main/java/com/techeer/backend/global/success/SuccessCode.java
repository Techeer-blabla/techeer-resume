package com.techeer.backend.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    SUCCESS(HttpStatus.OK, "200", "요청이 성공적으로 처리되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "204", "성공적으로 삭제되었습니다."),
    ;
    private HttpStatus status;
    private String code;
    private String message;
}