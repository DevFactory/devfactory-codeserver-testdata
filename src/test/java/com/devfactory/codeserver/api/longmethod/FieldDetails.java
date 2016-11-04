package com.devfactory.codeserver.api.longmethod;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FieldDetails {

    public enum Visibility {
        PUBLIC("Public"),
        PROTECTED("Protected"),
        PRIVATE("Private");

        private String value;

        Visibility(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Visibility findByVal(String name) {
            for (Visibility visibility : Visibility.values()) {
                if (name.equals(visibility.getValue())) {
                    return visibility;
                }
            }
            throw new IllegalArgumentException("Invalid Visibility enum");
        }
    }

    private Long id;

    private String name;
    private int numberOfUseByRefs;
    private String type;
    private String className;
    private String classUniqueName;
    private String longName;
    private String pkg;

    private String uniqueName;

    private int line;
    private String fileName;
    private String kind;
    private String decl;
    private boolean isPrimitive;
    private Visibility visibility;
    private int usedBy;
    private int setBy;
    //only available for method parameters
    private int methodStartLine;
    private int methodEndLine;
    private String methodName;

    private String callerSequence;
    private String useBySequence;

    private int setWithoutInit;
    private int fileSize;

    //method uniqueName | number of uses,
    private Set<String> useBySequenceSameClass = new HashSet<>();

    private Long moduleId;

    private ClassDetails classNode;

    private MethodDetails methodNode;

    private MethodDetails parameterMethodNode;

    public FieldDetails(String name, int numberOfUseByRefs, String type, Long moduleId, String className, String longName, String fileName) {
        this.name = name;
        this.numberOfUseByRefs = numberOfUseByRefs;
        this.type = type;
        this.moduleId = moduleId;
        this.className = className;
        this.longName = longName;
        this.fileName = fileName;
    }
}
