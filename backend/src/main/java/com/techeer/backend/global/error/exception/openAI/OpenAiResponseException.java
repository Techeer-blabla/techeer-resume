package com.techeer.backend.global.error.exception.openAI;

import com.techeer.backend.global.error.ErrorCode;
import com.techeer.backend.global.error.exception.BusinessException;

public class OpenAiResponseException extends BusinessException {

  public OpenAiResponseException(){ super(ErrorCode.OPENAI_INVALID_RESPONSE);}
}
