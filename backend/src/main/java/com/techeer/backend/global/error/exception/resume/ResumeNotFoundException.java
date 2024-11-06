package com.techeer.backend.global.error.exception.resume;

import com.techeer.backend.global.error.ErrorStatus;
import com.techeer.backend.global.error.exception.BusinessException;

public class ResumeNotFoundException extends BusinessException {

    public ResumeNotFoundException() {
        super(ErrorStatus.RESUME_NOT_FOUND);
    }
}
