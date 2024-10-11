package com.techeer.backend.global.error.exception.openAI;

import com.techeer.backend.global.error.ErrorCode;
import com.techeer.backend.global.error.exception.BusinessException;

public class OpenAiServerException extends BusinessException {

  public OpenAiServerException(){super(ErrorCode.OPENAI_SERVER_ERROR);}
}
