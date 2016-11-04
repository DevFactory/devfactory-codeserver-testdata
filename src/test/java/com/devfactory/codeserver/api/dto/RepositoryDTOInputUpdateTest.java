package com.devfactory.codeserver.api.dto;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import com.devfactory.codeserver.api.ModelTest;
import com.devfactory.codeserver.api.dto.RepositoryDTO.RepositoryDTOInputUpdate;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to test entity.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RepositoryDTOInputUpdateTest extends ModelTest<RepositoryDTOInputUpdate> {

    public RepositoryDTOInputUpdateTest() {
        super(new TypeReference<RepositoryDTOInputUpdate>() {});
    }

    @Override
    public RepositoryDTOInputUpdate getInstance() {
        RepositoryDTOInputUpdate dto = new RepositoryDTOInputUpdate();
        dto.setName("any");
        dto.setParentId(1241111);
        dto.setEndDate(new Date(90909090));
        return dto;
    }

    @Override
    public void testProperties() {
        assertEquals("any", instance.getName());
        assertEquals(1241111, (long) instance.getParentId());
        assertEquals(new Date(90909090), instance.getEndDate());
    }

}
