package com.devfactory.codeserver.api;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import static org.junit.Assert.*;

/**
 * Class to test properties.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class CodeServerPropertiesTest extends ModelTest<CodeServerProperties> {

    public CodeServerPropertiesTest() {
        super(new TypeReference<CodeServerProperties>() {});
    }

    @Override
    public CodeServerProperties getInstance() {
        CodeServerProperties prop = new CodeServerProperties();
        prop.setBasePath("any");
        prop.setRepositoriesBasePath("any");
        prop.setRepositoriesDownloadableExtension("any");
        prop.setRepositoriesJson("any");
        prop.setRevisionsBasePath("any");
        prop.setRevisionsJson("any");
        prop.setSourceapi(new CodeServerProperties.SourceApi());
        return prop;
    }

    @Test
    @Override
    public void testProperties() {
        assertEquals("any", instance.getBasePath());
        assertEquals("any", instance.getRepositoriesBasePath());
        assertEquals("any", instance.getRepositoriesDownloadableExtension());
        assertEquals("any", instance.getRepositoriesJson());
        assertEquals("any", instance.getRevisionsBasePath());
        assertEquals("any", instance.getRevisionsJson());
        assertNotNull(instance.getSourceapi());
    }

}
