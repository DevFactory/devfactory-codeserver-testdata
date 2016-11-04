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
public class RevisionDTOInputSave extends ModelTest<RevisionDTO.RevisionDTOInputSave> {

    private static final Date ANY_DATE = new Date();

    public RevisionDTOInputSave() {
        super(new TypeReference<RevisionDTO.RevisionDTOInputSave>() {});
    }

    @Override
    public RevisionDTO.RevisionDTOInputSave getInstance() {
        RevisionDTO.RevisionDTOInputSave o = new RevisionDTO.RevisionDTOInputSave();
        o.setAuthor("any");
        o.setDate(ANY_DATE);
        o.setMessage("any");
        o.setScmRev("any");
        return o;
    }

    @Test
    @Override
    public void testProperties() {
        assertEquals("any", instance.getAuthor());
        assertEquals(ANY_DATE, instance.getDate());
        assertEquals("any", instance.getMessage());
        assertEquals("any", instance.getScmRev());
    }

}
