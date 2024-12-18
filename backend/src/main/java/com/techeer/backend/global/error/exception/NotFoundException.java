package com.techeer.backend.global.error.exception;

import com.techeer.backend.global.error.ErrorCode;

public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
