package com.techeer.backend.global.error.exception.resume;

import com.techeer.backend.global.error.ErrorCode;
import com.techeer.backend.global.error.exception.BusinessException;

public class ResumeUploadException extends BusinessException {

  public ResumeUploadException(){super(ErrorCode.RESUME_UPLOAD_ERROR);}
}
