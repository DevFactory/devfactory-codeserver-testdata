/**
 * @author yogesh.badke@aurea.com
 */
package com.devfactory.codeserver.api.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.devfactory.codeserver.api.BaseUnitTest;
import com.devfactory.codeserver.api.CodeServerProperties.ScmCredential;

public class ScmCredentialsRepositoryTest extends BaseUnitTest {

    @Autowired
    private ScmCredentialsRepository repository;

    @Test
    public void testGetRepository() throws MalformedURLException {
        URL url = new URL("https://scmurl.com");
        Optional<ScmCredential> optionalScmCredentials = repository.getScmCredentials(url);
        assertTrue(optionalScmCredentials.isPresent());
        ScmCredential scmCredential = optionalScmCredentials.get();
        assertTrue("doyouknow".equals(scmCredential.getUserName()));
        assertTrue("idontknow".equals(scmCredential.getPassword()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRepositoryWithException() {
        repository.getScmCredentials(null);
    }
    
    @Test
    public void testGetRepositoryWithNoResult() throws MalformedURLException{
        URL url = new URL("https://github.com");
        Optional<ScmCredential> optionalScmCredentials = repository.getScmCredentials(url);
        assertFalse(optionalScmCredentials.isPresent());   
    }
}
