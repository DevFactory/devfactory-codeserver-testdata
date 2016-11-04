package com.devfactory.codeserver.api.exception;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Class to test exception handler.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class GlobalExceptionHandlerTest {

    @Test
    public void testProcessHoundException_ApiException() {
        ErrorCode errorCode = ErrorCode.GIT_ERROR;
        ApiException exception = new ApiException("any", errorCode);
        GlobalExceptionHandler instance = new GlobalExceptionHandler();
        ResponseEntity<ErrorResponse> result = instance.processHoundException(exception);
        assertEquals(errorCode.getHttpCode(), result.getStatusCodeValue());
        assertEquals(errorCode.getMessage(), result.getBody().getError().getMessage());
    }

    @Test
    public void testProcessHoundException_MethodArgumentNotValidException() {
        ErrorCode errorCode = ErrorCode.INVALID_ARGUMENTS;

        BindingResult br = mock(BindingResult.class);
        when(br.toString()).thenReturn("ANY");
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(br);

        GlobalExceptionHandler instance = new GlobalExceptionHandler();
        ResponseEntity<ErrorResponse> result = instance.processHoundException(exception);
        assertEquals(errorCode.getHttpCode(), result.getStatusCodeValue());
        assertEquals(errorCode.getMessage() + " - ANY", result.getBody().getError().getMessage());
    }

}
