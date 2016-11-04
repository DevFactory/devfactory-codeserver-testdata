package com.devfactory.codeserver.api.dao;

import static org.junit.Assert.*;
import com.devfactory.codeserver.api.BaseUnitTest;
import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.EntityFactory;
import com.devfactory.codeserver.api.model.Repository;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class to test repository DB implementation.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RepositoryDBTest extends BaseUnitTest {

    @Autowired
    private RepositoryDB db;

    @Autowired
    private CodeServerProperties config;

    @Before
    public void before() throws IOException {
        File basePath = new File(config.getBasePath());
        FileUtils.deleteDirectory(basePath);
        db.getRepositories().clear();
        db.save();
    }

    @Test
    public void testNewId() {
        int id = db.newId();
        assertEquals(id + 1, db.newId());
        assertEquals(id + 2, db.newId());
        assertEquals(id + 3, db.newId());
    }

    @Test
    public void testGetRepositoriesAndSave() throws IOException {
        assertTrue(db.getRepositories().isEmpty());
        Repository repo = EntityFactory.newRepository("any", "any", "any");
        db.save(repo);
        assertEquals(1, db.getRepositories().size());

        Repository savedRepo = db.getRepositories().get(repo.getId());

        assertEquals("any", savedRepo.getName());
        assertEquals("any", savedRepo.getScmUrl());
        assertEquals(1, savedRepo.getLanguages().get("any"), 0);
    }
}
