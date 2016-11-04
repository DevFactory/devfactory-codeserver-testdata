package com.devfactory.codeserver.api;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class CodeServerConfigTest {

    @Test
    public void testSwaggerSettings() {
        CodeServerConfig config = new CodeServerConfig();
        assertNotNull(config.getModelMapper());
    }
}
