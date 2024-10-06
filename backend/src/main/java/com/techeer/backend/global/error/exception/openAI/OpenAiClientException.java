package com.techeer.backend.global.error.exception.openAI;

import com.techeer.backend.global.error.ErrorCode;
import com.techeer.backend.global.error.exception.BusinessException;

public class OpenAiClientException extends BusinessException {

  public OpenAiClientException() {super(ErrorCode.OPENAI_CLIENT_ERROR);}
}
