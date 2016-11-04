package com.devfactory.codeserver.api.model;

import static org.junit.Assert.assertEquals;

import com.devfactory.codeserver.api.ModelTest;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to test entity.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class SourceApiDetailsTest extends ModelTest<SourceApiDetails> {

    public SourceApiDetailsTest() {
        super(new TypeReference<SourceApiDetails>() {});
    }

    @Override
    public SourceApiDetails getInstance() {
        SourceApiDetails o = new SourceApiDetails();
        o.setError("any");
        o.setStatus(SourceApiDetails.Status.READY);
        return o;
    }

    @Override
    public void testProperties() {
        assertEquals("any", instance.getError());
        assertEquals(SourceApiDetails.Status.READY, instance.getStatus());
    }

}
