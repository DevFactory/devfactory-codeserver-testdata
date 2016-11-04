package com.devfactory.codeserver.api.dao;

import static org.junit.Assert.*;
import com.devfactory.codeserver.api.BaseUnitTest;
import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.EntityFactory;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.model.RevisionStatus;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class to test revision DB implementation.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RevisionDBTest extends BaseUnitTest {

    @Autowired
    private RevisionDB db;

    @Autowired
    private CodeServerProperties config;

    @Before
    public void before() throws IOException {
        File basePath = new File(config.getBasePath());
        FileUtils.deleteDirectory(basePath);
        db.getRevisions().clear();
        db.save();
    }

    @Test
    public void testGetRevisionsAndSave() throws IOException {
        assertTrue(db.getRevisions().isEmpty());
        Revision rev = EntityFactory.newRevision(123, "any", "any");
        db.save(rev);

        assertEquals(1, db.getRevisions().size());

        Revision savedRev = db.getRevisions().get(rev.getId());

        assertEquals(123,  (int)savedRev.getRepositoryId());
        assertEquals("any", savedRev.getScmRev());
        assertEquals("any", savedRev.getAuthor());
    }

    @Test
    public void updateRevision() throws IOException {
        assertTrue(db.getRevisions().isEmpty());

        Revision rev = EntityFactory.newRevision(123, "any", "any");
        db.save(rev);

        assertEquals(1, db.getRevisions().size());

        rev = db.getRevisions().get(rev.getId());
        rev.analyzed();
        db.save(rev);

        assertEquals(1, db.getRevisions().size());

        rev = db.getRevisions().get(rev.getId());

        assertEquals(RevisionStatus.ANALYZED, rev.getStatus());
    }

}
