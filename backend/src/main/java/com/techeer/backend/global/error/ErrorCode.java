package com.techeer.backend.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // global
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G001", "서버 오류"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "G002", "오브젝트르 찾을 수 없다."),
    ;

    private HttpStatus status;
    private String code;
    private String message;
}