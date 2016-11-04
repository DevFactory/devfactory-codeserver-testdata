package com.devfactory.codeserver.api;

import static com.devfactory.codeserver.api.CodeServerProperties.DATE_FORMAT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.devfactory.codeserver.api.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to provide functionality for testing model classes.
 *
 * @param <E>
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public abstract class ModelTest<E extends Object> {
    private final TypeReference<E> ref;
    protected E instance;

    public ModelTest(TypeReference<E> ref) {
        this.ref = ref;
    }

    @Before
    public void before() {
        this.instance = getInstance();
    }

    public abstract E getInstance();

    @Test
    public abstract void testProperties();

    @Test
    public void testEquals() throws IOException {
        String json = JsonUtils.toJson(instance, DATE_FORMAT);
        E e = JsonUtils.fromJson(json, DATE_FORMAT, ref);
        assertEquals(instance, e);
        assertNotEquals(instance, new Object());
        assertNotEquals(instance, null);
        assertNotEquals(null, instance);
    }

    @Test
    public void testHashCode() throws IOException {
        String json = JsonUtils.toJson(instance, DATE_FORMAT);
        E e = JsonUtils.fromJson(json, DATE_FORMAT, ref);
        assertEquals(e.hashCode(), instance.hashCode());
        assertNotEquals(instance, new Object().hashCode());
        assertNotEquals(null, instance.hashCode());
        assertNotEquals(instance.hashCode(), null);
    }

    @Test
    public void testToString() {
        assertNotNull(instance.toString());
    }

}
