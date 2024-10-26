package com.techeer.backend.global.error.exception.comment;

import com.techeer.backend.global.error.ErrorCode;
import com.techeer.backend.global.error.exception.BusinessException;

public class CommentNotFoundException extends BusinessException {

  public CommentNotFoundException(){super(ErrorCode.COMMENT_NOT_FOUND_MESSAGE);}
}
