package com.devfactory.codeserver.api.dao;

import static org.junit.Assert.*;
import com.devfactory.codeserver.api.BaseUnitTest;
import com.devfactory.codeserver.api.CodeServerProperties;
import java.io.File;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class to test DB helper, which is temporary implementation of database.F
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class DBHelperTest extends BaseUnitTest {

    @Autowired
    private CodeServerProperties config;

    @Autowired
    private DBHelper dbhelper;

    @Test
    public void testCreateBaseFolders() throws Exception {
        File basePath = new File(config.getBasePath());
        File repositoriesBasePath = new File(config.getRepositoriesBasePath());
        File revisionsBasePath = new File(config.getRevisionsBasePath());

        FileUtils.deleteDirectory(basePath);
        assertTrue(!basePath.exists());

        dbhelper.createBaseFolders();
        assertTrue(basePath.exists());
        assertTrue(repositoriesBasePath.exists());
        assertTrue(revisionsBasePath.exists());
    }

}
