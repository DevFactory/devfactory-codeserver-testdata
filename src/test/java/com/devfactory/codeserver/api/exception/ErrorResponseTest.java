package com.devfactory.codeserver.api.exception;

import static org.junit.Assert.assertEquals;

import com.devfactory.codeserver.api.ModelTest;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to test error response.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class ErrorResponseTest extends ModelTest<ErrorResponse> {

    public ErrorResponseTest() {
        super(new TypeReference<ErrorResponse>() {});
    }

    @Override
    public ErrorResponse getInstance() {
        ErrorResponse r = new ErrorResponse();
        r.setError(new ErrorResponse.Error(404, 1010, "any"));
        return r;
    }

    @Override
    public void testProperties() {
        assertEquals(404, instance.getError().getHttp());
        assertEquals(1010, instance.getError().getCode());
        assertEquals("any", instance.getError().getMessage());
    }

}
