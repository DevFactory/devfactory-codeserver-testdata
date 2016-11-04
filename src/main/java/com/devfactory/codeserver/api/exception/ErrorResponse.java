package com.devfactory.codeserver.api.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
public class ErrorResponse {

    private Error error;

    @Getter
    @AllArgsConstructor(access = AccessLevel.PUBLIC)
    @EqualsAndHashCode
    public static class Error {

        private final int http;

        private final int code;

        private final String message;

    }

}
