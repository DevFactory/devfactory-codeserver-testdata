package com.devfactory.codeserver.api.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.devfactory.codeserver.api.CodeServerProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Class with utility static methods to manipulate Json data.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {
    private final static ObjectMapper DEFAULT_MAPPER = new ObjectMapper();

    static {
        DateFormat df = new SimpleDateFormat(CodeServerProperties.DATE_FORMAT);
        DEFAULT_MAPPER.setDateFormat(df);
        DEFAULT_MAPPER.setTimeZone(TimeZone.getDefault());
        DEFAULT_MAPPER.findAndRegisterModules();
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) throws IOException {
        return DEFAULT_MAPPER.readValue(json, typeReference);
    }

    public static <T> T fromJson(String json, String dateFormat, TypeReference<T> typeReference) throws IOException {
        return getMapperWithDateFormatter(dateFormat).readValue(json, typeReference);
    }

    public static String toJson(Object object) throws IOException {
        return DEFAULT_MAPPER.writeValueAsString(object);
    }

    public static String toJson(Object object, String dateFormat) throws IOException {
        return getMapperWithDateFormatter(dateFormat).writeValueAsString(object);
    }

    private static ObjectMapper getMapperWithDateFormatter(String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat(dateFormat);
        mapper.setDateFormat(df);
        mapper.setTimeZone(TimeZone.getDefault());
        mapper.findAndRegisterModules();
        return mapper;
    }
}
