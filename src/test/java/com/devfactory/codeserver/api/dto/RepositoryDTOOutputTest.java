package com.devfactory.codeserver.api.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.Date;

import com.devfactory.codeserver.api.ModelTest;
import com.devfactory.codeserver.api.dto.RepositoryDTO.RepositoryDTOOutput;
import com.devfactory.codeserver.api.model.RepositoryStatus;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to test entity.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RepositoryDTOOutputTest extends ModelTest<RepositoryDTOOutput> {

    public RepositoryDTOOutputTest() {
        super(new TypeReference<RepositoryDTOOutput>() {});
    }

    @Override
    public RepositoryDTOOutput getInstance() {
        RepositoryDTOOutput dto = new RepositoryDTOOutput();
        dto.setBranch("any");
        dto.setCreate(Boolean.TRUE);
        dto.setEndDate(new Date(10101010));
        RevisionDTO.RevisionDTOOutput headRevision = new RevisionDTO.RevisionDTOOutput();
        headRevision.setScmRev("svm-rev-id");
        dto.setHeadRevision(headRevision);
        dto.setId(132);
        dto.setLanguage(Collections.singletonMap("Java", 1d));
        dto.setName("any");
        dto.setParentId(1241111);
        dto.setScmUrl("any");
        dto.setShortUrl("any");
        dto.setStartDate(new Date(10101010));
        dto.setStatus(RepositoryStatus.notInitialized());
        return dto;
    }

    @Override
    public void testProperties() {
        assertEquals("any", instance.getBranch());
        assertEquals(true, instance.getCreate());
        assertEquals(new Date(10101010), instance.getEndDate());
        assertEquals("svm-rev-id", instance.getHeadRevision().getScmRev());
        assertEquals(132, (long) instance.getId());
        assertEquals(Collections.singletonMap("Java", 1d), instance.getLanguage());
        assertEquals("any", instance.getName());
        assertEquals(1241111, (long) instance.getParentId());
        assertEquals("any", instance.getScmUrl());
        assertEquals("any", instance.getShortUrl());
        assertEquals(new Date(10101010), instance.getStartDate());
        assertNotNull(instance.getStatus());
    }

}
