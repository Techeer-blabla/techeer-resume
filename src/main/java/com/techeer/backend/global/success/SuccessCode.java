package com.techeer.backend.global.success;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    ;
    private HttpStatus status;
    private String code;
    private String message;
}