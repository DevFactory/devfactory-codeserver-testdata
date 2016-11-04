package com.devfactory.codeserver.api.dto;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.devfactory.codeserver.api.ModelTest;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to test entity.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RevisionDTOOutputTest extends ModelTest<RevisionDTO.RevisionDTOOutput> {

    private static final Date ANY_DATE = new Date();

    public RevisionDTOOutputTest() {
        super(new TypeReference<RevisionDTO.RevisionDTOOutput>() {});
    }

    @Override
    public RevisionDTO.RevisionDTOOutput getInstance() {
        RevisionDTO.RevisionDTOOutput o = new RevisionDTO.RevisionDTOOutput();
        o.setAuthor("any");
        o.setDate(ANY_DATE);
        o.setId("234");
        o.setMessage("any");
        o.setScmRev("any");
        return o;
    }

    @Test
    @Override
    public void testProperties() {
        assertEquals("any", instance.getAuthor());
        assertEquals(ANY_DATE, instance.getDate());
        assertEquals("234",  instance.getId());
        assertEquals("any", instance.getMessage());
        assertEquals("any", instance.getScmRev());
    }

}
