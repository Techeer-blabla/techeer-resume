package com.techeer.backend.global.error.exception;

import com.techeer.backend.global.error.ErrorStatus;

public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super(ErrorStatus.NOT_FOUND);
    }
}
