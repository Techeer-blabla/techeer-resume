package com.techeer.backend.global.error.exception.feedback;

import com.techeer.backend.global.error.ErrorCode;
import com.techeer.backend.global.error.exception.BusinessException;

public class InvalidPageNumberException extends BusinessException {

  public InvalidPageNumberException() {
    super(ErrorCode.INVALID_FEEDBACK_PAGE_NUMBER);
  }
}
