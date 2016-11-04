package com.devfactory.codeserver.api.service.impl;

import com.devfactory.codeserver.api.BaseUnitTest;
import com.devfactory.codeserver.api.CodeServerProperties;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Class to test helper for projects.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RepositoryHelperTest extends BaseUnitTest {

    @Autowired
    private CodeServerProperties config;

    @Autowired
    private RepositoryHelper repositoryHelper;

    @Before
    public void setUp() throws IOException {
        Path folder = Paths.get(config.getBasePath());
        FileUtils.deleteDirectory(folder.toFile());
    }

    @Test
    public void testPathToRepository() {
        assertEquals(
                config.getRepositoriesBasePath().concat(File.separator).concat("100").concat(File.separator).concat("local"),
                repositoryHelper.pathToRepository(100));
    }
}
