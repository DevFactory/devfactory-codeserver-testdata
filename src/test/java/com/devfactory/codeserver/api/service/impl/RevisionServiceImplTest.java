package com.devfactory.codeserver.api.service.impl;

import static org.junit.Assert.*;
import com.devfactory.codeserver.api.BaseUnitTest;
import com.devfactory.codeserver.api.EntityFactory;
import com.devfactory.codeserver.api.exception.ApiException;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class to test service implementation.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RevisionServiceImplTest extends BaseUnitTest {

    @Autowired
    private RevisionServiceImpl revService;

    @Autowired
    private RepositoryServiceImpl repoService;

    @Before
    public void before() throws Exception {
        repoService.deleteAll();
    }

    @Test(expected = ApiException.class)
    public void findAllRepoDoesNotExist() throws Exception {
        assertTrue(revService.findAll(123).isEmpty());
    }

    @Test
    public void findAllEmpty() throws Exception {
        Integer repoId = repoService.save(EntityFactory.newRepository());
        assertTrue(revService.findAll(repoId).isEmpty());
    }

    @Test
    public void findNonExistent() throws Exception {
        Integer repoId = repoService.save(EntityFactory.newRepository());
        assertNull(revService.find(repoId, 123123));
    }

    @Test
    public void save() throws Throwable {
        Integer repoId = repoService.save(EntityFactory.newRepository());
        revService.save(repoId, EntityFactory.newRevision());
        revService.save(repoId, EntityFactory.newRevision());
    }

    @Test
    public void findAll_AfterSave() throws Throwable {
        Integer repoId = repoService.save(EntityFactory.newRepository());
        revService.save(repoId, EntityFactory.newRevision(231, "any", "any"));
        revService.save(repoId, EntityFactory.newRevision(232, "any2", "any2"));
        assertEquals(2, revService.findAll(repoId).size());
    }
}
