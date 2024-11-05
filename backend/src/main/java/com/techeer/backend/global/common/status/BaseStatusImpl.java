package com.techeer.backend.global.common.status;

import com.techeer.backend.global.common.response.ReasonDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseStatusImpl implements BaseStatus {
    private final ReasonDto reason;

    @Builder
    public BaseStatusImpl(ReasonDto reason) {
        this.reason = reason;
    }

    @Override
    public ReasonDto getReason() {
        return reason;
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return reason;
    }
}
