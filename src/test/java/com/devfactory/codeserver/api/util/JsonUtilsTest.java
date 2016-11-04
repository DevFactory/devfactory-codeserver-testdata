package com.devfactory.codeserver.api.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.devfactory.codeserver.api.BaseUnitTest;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to test JSON-Object functionality.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class JsonUtilsTest extends BaseUnitTest {

    private final Map<String, Object> o1;

    private final String j1;

    public JsonUtilsTest() {
        o1 = new HashMap<>();
        o1.put("field1", "value1");
        o1.put("field2", new HashMap<>());
        ((HashMap) o1.get("field2")).put("field2.1", "value2.1");
        ((HashMap) o1.get("field2")).put("field2.2", "value2.2");

        j1 = "{\"field1\":\"value1\",\"field2\":{\"field2.2\":\"value2.2\",\"field2.1\":\"value2.1\"}}";
    }

    /**
     * Test of fromJson method, of class JsonUtils.
     * @throws IOException 
     */
    @Test
    public void testFromJson() throws IOException {
        Map<String, Object> o = JsonUtils.fromJson(j1, new TypeReference<Map<String, Object>>() {});
        assertEquals(o1, o);
    }

    /**
     * Test of toJson method, of class JsonUtils.
     * @throws IOException 
     */
    @Test
    public void testToJson() throws IOException {
        assertEquals(j1, JsonUtils.toJson(o1));
    }

}
