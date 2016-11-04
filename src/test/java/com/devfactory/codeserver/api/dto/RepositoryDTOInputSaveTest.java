package com.devfactory.codeserver.api.dto;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import com.devfactory.codeserver.api.ModelTest;
import com.devfactory.codeserver.api.dto.RepositoryDTO.RepositoryDTOInputSave;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to test entity.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RepositoryDTOInputSaveTest extends ModelTest<RepositoryDTOInputSave> {

    public RepositoryDTOInputSaveTest() {
        super(new TypeReference<RepositoryDTOInputSave>() {});
    }

    @Override
    public RepositoryDTOInputSave getInstance() {
        RepositoryDTOInputSave dto = new RepositoryDTOInputSave();
        dto.setCreate(Boolean.TRUE);
        dto.setName("any");
        dto.setParentId(1241111);
        dto.setScmUrl("any");
        dto.setValidDate(new Date(10101010));
        return dto;
    }

    @Override
    public void testProperties() {
        assertEquals(true, instance.getCreate());
        assertEquals("any", instance.getName());
        assertEquals(1241111, (long) instance.getParentId());
        assertEquals("any", instance.getScmUrl());
        assertEquals(new Date(10101010), instance.getValidDate());
    }

}
