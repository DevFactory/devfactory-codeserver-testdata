package com.devfactory.codeserver.api;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class SwaggerConfigTest {

    @Test
    public void testSwaggerSettings() {
        assertNotNull(new SwaggerConfig().swaggerSettings());
    }

}
