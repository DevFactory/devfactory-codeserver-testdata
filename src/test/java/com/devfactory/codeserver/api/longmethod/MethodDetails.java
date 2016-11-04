package com.devfactory.codeserver.api.longmethod;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MethodDetails {

    private Long id;
    private String file;
    private String relFile;
    private int priority;
    private int countLineCode;
    private int beginLine = -1;
    private int endLine = -1;
    private String pkg = "";
    private int methodLengthMinThreshold;
    private int methodLengthMaxThreshold;
    private String methodSignature;
    private String methodName;
    private AntipatternAlgo algo;

    public MethodDetails(AntipatternAlgo algo) {
        this.algo = algo;
    }

    private List<FieldDetails> parameterFields = new ArrayList<>();

    public int getParamFieldsSize() {
        if (parameterFields == null) {
            return 0;
        }
        return parameterFields.size();
    }

    public int getSeverity() {
        switch (algo) {
            case PRIMITIVE_OBSESSION:
                return getParamFieldsSize() /* + priority*/;
            case LONG_METHOD:
                return getPriority();
            case LONG_PARAMETER_LIST:
                return getPriority();
            default:
                return -1;
        }
    }
}
