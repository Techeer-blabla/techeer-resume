package com.techeer.backend.global.error.exception.comment;

import com.techeer.backend.global.error.ErrorCode;
import com.techeer.backend.global.error.exception.BusinessException;

public class NoCommentsForResumeException extends BusinessException {

  public NoCommentsForResumeException(){super(ErrorCode.COMMENT_NOT_FOUND);}
}
