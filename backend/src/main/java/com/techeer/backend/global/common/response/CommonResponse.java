package com.techeer.backend.global.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.techeer.backend.global.common.status.BaseStatus;
import com.techeer.backend.global.success.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "result"})
public class CommonResponse<T> {

    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    // 성공시 응답 생성
    public static <T> CommonResponse<T> onSuccess(T result) {
        return new CommonResponse<>(SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), result);
    }

    public static <T> CommonResponse<T> of(BaseStatus status, T result) {
        return new CommonResponse<>(status.getReason().getCode(), status.getReason().getMessage(), result);
    }

    // 실패시 응답 생성
    public static <T> CommonResponse<T> onFailure(String code, String message, T data) {
        return new CommonResponse<>(code, message, data);
    }

}