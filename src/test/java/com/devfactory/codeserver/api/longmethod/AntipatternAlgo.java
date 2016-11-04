package com.devfactory.codeserver.api.longmethod;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AntipatternAlgo {

    LONG_METHOD("long_method"),
    LONG_PARAMETER_LIST("long_parameter"),
    PRIMITIVE_OBSESSION("primitive_obsession");

    private static final Map<String, AntipatternAlgo> namesMap = new HashMap<>(2);

    static {
        namesMap.put("long_method", LONG_METHOD);
        namesMap.put("long_parameter", LONG_PARAMETER_LIST);
        namesMap.put("primitive_obsession", PRIMITIVE_OBSESSION);
    }

    private final String name;

    public static AntipatternAlgo forValue(String value) {
        return namesMap.get(StringUtils.lowerCase(value));
    }
    
}
