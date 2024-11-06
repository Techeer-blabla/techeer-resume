package com.techeer.backend.global.error.exception;


import com.techeer.backend.global.error.ErrorStatus;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public BusinessException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}