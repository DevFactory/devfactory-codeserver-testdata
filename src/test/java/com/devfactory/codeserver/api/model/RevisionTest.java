package com.devfactory.codeserver.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;
import java.util.Date;

import com.devfactory.codeserver.api.ModelTest;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to test entity.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RevisionTest extends ModelTest<Revision> {

    public RevisionTest() {
        super(new TypeReference<Revision>() {});
    }

    @Override
    public Revision getInstance() {
        Revision rev = new Revision();
        rev.setAuthor("any");
        rev.setDate(new Date(90909090));
        rev.setMessage("any");
        rev.setRepositoryId(42);
        rev.setScmRev("any");
        rev.setCommitTime(ZonedDateTime.now());
        return rev;
    }

    @Override
    public void testProperties() {
        assertEquals("any", instance.getAuthor());
        assertEquals(new Date(90909090), instance.getDate());
        assertNotNull(instance.getId());
        assertEquals("any", instance.getMessage());
        assertEquals(42, (int) instance.getRepositoryId());
        assertEquals("any", instance.getScmRev());
    }

}
