package com.devfactory.codeserver.api.service.impl;

import static org.junit.Assert.*;
import com.devfactory.codeserver.api.BaseUnitTest;
import com.devfactory.codeserver.api.EntityFactory;
import com.devfactory.codeserver.api.model.Repository;
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
public class RepositoryServiceImplTest extends BaseUnitTest {

    @Autowired
    private RepositoryServiceImpl service;

    @Before
    public void before() throws Exception {
        service.deleteAll();
    }

    @Test
    public void findAll_Empty() throws Exception {
        assertTrue(service.findAll().isEmpty());
    }

    @Test
    public void find_NotExistent() throws Exception {
        assertNull(service.find(123123));
    }

    @Test
    public void save() throws Throwable {
        service.save(EntityFactory.newRepository("ProjA", "anyUrl1", "Java"));
        service.save(EntityFactory.newRepository("ProjB", "anyUrl2", "C#"));
    }

    @Test
    public void findAll_AfterSave() throws Throwable {
        Repository repo1 = EntityFactory.newRepository("ProjA", "anyUrl1", "Java");
        Integer repo1Id = service.save(repo1);

        Repository repo2 = EntityFactory.newRepository("ProjB", "anyUrl2", "C#");
        Integer repo2Id = service.save(repo2);

        assertEquals(2, service.findAll().size());

        repo1 = service.find(repo1Id);
        assertEquals("ProjA", repo1.getName());
        assertEquals("anyUrl1", repo1.getScmUrl());
        assertEquals(1d, repo1.getLanguages().get("Java"), 0);

        repo2 = service.find(repo2Id);
        assertEquals("ProjB", repo2.getName());
        assertEquals(1d, repo2.getLanguages().get("C#"), 0);
        assertEquals("anyUrl2", repo2.getScmUrl());
    }

    @Test
    public void find_Existent() throws Throwable {
        Integer id1 = service.save(EntityFactory.newRepository("ProjA", "anyUrl1", "Java"));
        Integer id2 = service.save(EntityFactory.newRepository("ProjB", "anyUrl2", "C#"));
        assertNotNull(service.find(id1));
        assertEquals("ProjA", service.find(id1).getName());
        assertEquals("anyUrl1", service.find(id1).getScmUrl());
        assertNotNull(service.find(id2));
        assertEquals("ProjB", service.find(id2).getName());
        assertEquals("anyUrl2", service.find(id2).getScmUrl());
    }

    @Test
    public void saveUpdateExistent() throws Throwable {
        Integer id = service.save(EntityFactory.newRepository("ProjA", "anyUrl1", "Java"));
        Repository r = EntityFactory.newRepository("ProjB", "anyUrl1", "Java");
        service.save(r);
        assertNotNull(service.find(id));
        assertEquals("ProjB", service.find(id).getName());
    }

    @Test
    public void update() throws Throwable {
        Integer id = service.save(EntityFactory.newRepository("ProjA", "anyUrl1", "Java"));
        Repository r = service.find(id);
        r.setName("ProjC");
        service.update(id, r);
        assertNotNull(service.find(id));
        assertEquals("ProjC", service.find(id).getName());
    }

    @Test
    public void deleteAll() throws Throwable {
        assertTrue(service.findAll().isEmpty());
        service.save(EntityFactory.newRepository("ProjA", "anyUrl1", "Java"));
        service.save(EntityFactory.newRepository("ProjB", "anyUrl2", "C#"));
        assertEquals(2, service.findAll().size());
        service.deleteAll();
        assertEquals(0, service.findAll().size());
    }

    @Test
    public void delete() throws Throwable {
        assertTrue(service.findAll().isEmpty());
        Integer id = service.save(EntityFactory.newRepository("ProjA", "anyUrl1", "Java"));
        assertEquals(1, service.findAll().size());
        service.delete(id);
        assertEquals(0, service.findAll().size());
    }

}
