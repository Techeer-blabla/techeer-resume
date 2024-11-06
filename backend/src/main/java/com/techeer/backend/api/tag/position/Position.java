package com.techeer.backend.api.tag.position;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.ToString;

@ToString
public enum Position {
    BACKEND("BACKEND"),
    FRONTEND("FRONTEND"),
    ANDROID("ANDROID"),
    IOS("IOS"),
    DEVOPS("DEVOPS");

    private final String value;

    Position(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}