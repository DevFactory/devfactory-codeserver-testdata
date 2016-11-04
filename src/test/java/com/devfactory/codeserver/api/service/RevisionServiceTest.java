package com.devfactory.codeserver.api.service;

import com.devfactory.codeserver.api.BaseUnitTest;
import com.devfactory.codeserver.api.service.impl.RevisionServiceImpl;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class to test the interface relation to the implementation.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RevisionServiceTest extends BaseUnitTest {

    @Autowired
    private RevisionService service;

    @Test
    public void construct() throws Exception {
        assertTrue(service instanceof RevisionServiceImpl);
    }

}
