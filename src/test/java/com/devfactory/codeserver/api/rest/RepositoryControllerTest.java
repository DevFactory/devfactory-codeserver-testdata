package com.devfactory.codeserver.api.rest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.devfactory.codeserver.api.BaseMockUnitTest;
import com.devfactory.codeserver.api.dto.RepositoryDTO.RepositoryDTOInputSave;
import com.devfactory.codeserver.api.dto.RepositoryDTO.RepositoryDTOInputUpdate;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.RepositoryCheckoutStatus;
import com.devfactory.codeserver.api.model.RepositoryStatus;
import com.devfactory.codeserver.api.service.RepositoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class to test rest controller.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RepositoryControllerTest extends BaseMockUnitTest {

    @Autowired
    private RepositoryService service;

    @Autowired
    private RepositoryController controller;

    @Test
    public void testFindAll() throws Exception {
        controller.findAll();
        verify(service, atLeast(1)).findAll();
    }

    @Test
    public void testFindNonExistent() throws Throwable {
        when(service.find(999)).thenReturn(null);

        assertEquals(404, controller.find(999).getStatusCodeValue());
        verify(service, atLeast(1)).find(10101);
    }

    @Test
    public void testFindImporting() throws Throwable {
        Repository repo = new Repository();
        repo.setName("any");
        repo.setScmUrl("any");
        repo.setStatus(new RepositoryStatus());
        repo.getStatus().setCheckoutStatus(RepositoryCheckoutStatus.IMPORTING);
        when(service.find(10101)).thenReturn(repo);

        assertEquals(404, controller.find(10101).getStatusCodeValue());
        verify(service, atLeast(1)).find(10101);
    }

    @Test
    public void testFindReady() throws Throwable {
        Repository repo = new Repository();
        repo.setName("any");
        repo.setScmUrl("any");
        repo.setStatus(new RepositoryStatus());
        repo.getStatus().setCheckoutStatus(RepositoryCheckoutStatus.READY);
        when(service.find(10101)).thenReturn(repo);

        assertEquals(200, controller.find(10101).getStatusCodeValue());
        verify(service, atLeast(1)).find(10101);
    }

    @Test
    public void testSaveAccept() throws Throwable {
        Repository repo = new Repository();
        repo.setName("anyname");
        repo.setScmUrl("anyurl");
        repo.setStatus(new RepositoryStatus());
        repo.getStatus().setCheckoutStatus(RepositoryCheckoutStatus.NOT_INITIALIZED);
        when(service.find(10101)).thenReturn(repo);
        when(service.save(any(Repository.class))).thenReturn(10101);

        RepositoryDTOInputSave dto = new RepositoryDTOInputSave();
        dto.setName("anyname");
        dto.setScmUrl("anyurl");
        assertEquals(202, controller.save(dto).getStatusCodeValue());
        verify(service, atLeast(1)).save(any(Repository.class));
    }
    
    
    @Test
    public void testSaveReady() throws Throwable {
        Repository repo = new Repository();
        repo.setName("anyname");
        repo.setScmUrl("anyurl");
        repo.setStatus(new RepositoryStatus());
        repo.getStatus().setCheckoutStatus(RepositoryCheckoutStatus.READY);
        when(service.find(10101)).thenReturn(repo);
        when(service.save(any(Repository.class))).thenReturn(10101);

        RepositoryDTOInputSave dto = new RepositoryDTOInputSave();
        dto.setName("anyname");
        dto.setScmUrl("anyurl");
        assertEquals(200, controller.save(dto).getStatusCodeValue());
        verify(service, atLeast(1)).save(any(Repository.class));
    }

    @Test
    public void testUpdate() throws Throwable {
        Repository r = new Repository();
        when(service.find(22)).thenReturn(r);

        RepositoryDTOInputUpdate dto = new RepositoryDTOInputUpdate();
        dto.setName("anyname");

        controller.update(22, dto);
        verify(service, atLeast(1)).update(22, r);
    }

    @Test
    public void testDeleteAll() throws Throwable {
        controller.deleteAll();
        verify(service, atLeast(1)).deleteAll();
    }

    @Test
    public void testDelete() throws Throwable {
        controller.delete(92);
        verify(service, atLeast(1)).delete(92);
    }

}
