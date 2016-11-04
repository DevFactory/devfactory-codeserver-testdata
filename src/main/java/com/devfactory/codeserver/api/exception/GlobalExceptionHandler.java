package com.devfactory.codeserver.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> processHoundException(ApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse response = new ErrorResponse();
        response.setError(new ErrorResponse.Error(
                errorCode.getHttpCode(),
                errorCode.getCode(),
                errorCode.getMessage()));

        log.error(response.toString());

        return ResponseEntity
                .status(errorCode.getHttpCode() != 0 ? errorCode.getHttpCode() : 500)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> processHoundException(MethodArgumentNotValidException exception) {
        ErrorCode errorCode = ErrorCode.INVALID_ARGUMENTS;
        ErrorResponse response = new ErrorResponse();
        response.setError(new ErrorResponse.Error(
                errorCode.getHttpCode(),
                errorCode.getCode(),
                errorCode.getMessage() + " - " + exception.getBindingResult().toString()));

        log.error(response.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
