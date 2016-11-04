package com.devfactory.codeserver.api.dto;

import static org.junit.Assert.assertEquals;

import com.devfactory.codeserver.api.ModelTest;
import com.devfactory.codeserver.api.dto.SourceApiDetailsDTO.SourceApiDetailsDTOOutput;
import com.devfactory.codeserver.api.model.SourceApiDetails;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to test entity.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class SourceApiDetailsDTOOutputTest extends ModelTest<SourceApiDetailsDTOOutput> {

    public SourceApiDetailsDTOOutputTest() {
        super(new TypeReference<SourceApiDetailsDTOOutput>() {});
    }

    @Override
    public SourceApiDetailsDTOOutput getInstance() {
        SourceApiDetailsDTOOutput o = new SourceApiDetailsDTOOutput();
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
