package com.zacharyhuang.accountValidation.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    InvalidRequest("invalid_request"),
    InternalServerError("internal_server_error");

    private final String value;

    ErrorCode(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonValue
    public String toString() {
        return this.getValue();
    }
}
