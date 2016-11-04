package com.devfactory.codeserver.api.exception;

import lombok.Getter;

public class ApiException extends Exception {

    @Getter
    private final ErrorCode errorCode;

    public ApiException(String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

}
