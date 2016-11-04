package com.devfactory.codeserver.api.exception;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class to test exception.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class ApiExceptionTest {

    @Test
    public void testGetErrorCode() {
        assertEquals(ErrorCode.COMMAND_EXECUTION_FAILED, new ApiException("any", ErrorCode.COMMAND_EXECUTION_FAILED).getErrorCode());
    }

}
