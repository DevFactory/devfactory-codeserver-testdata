package com.devfactory.codeserver.api.service;

import static org.junit.Assert.*;
import com.devfactory.codeserver.api.BaseUnitTest;
import com.devfactory.codeserver.api.service.impl.RepositoryServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class to test the interface relation to the implementation.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RepositoryServiceTest extends BaseUnitTest {

    @Autowired
    private RepositoryService service;

    @Test
    public void construct() throws Exception {
        assertTrue(service instanceof RepositoryServiceImpl);
    }

}
