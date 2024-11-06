package com.techeer.backend.global.error.exception.resume;

import com.techeer.backend.global.error.ErrorStatus;
import com.techeer.backend.global.error.exception.BusinessException;

public class ResumeUploadException extends BusinessException {

    public ResumeUploadException() {
        super(ErrorStatus.RESUME_UPLOAD_ERROR);
    }
}
