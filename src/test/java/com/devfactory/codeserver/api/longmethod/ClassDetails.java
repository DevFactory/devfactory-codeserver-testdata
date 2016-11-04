package com.devfactory.codeserver.api.longmethod;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class ClassDetails {

    private int id;
    private String name;
    private String className;
    private String shortName;
    private String fileName;
    private String packageLongName;
    private List<MethodDetails> methods = new ArrayList<>();
    @Setter
    private List<FieldDetails> fields = new ArrayList<>();
    @Setter //TODO check if required
    private List<String> allFiles;

    public ClassDetails(int id, String name, String className, String shortName, String fileName,
            List<MethodDetails> methods, String packageLongName) {
        super();
        this.id = id;
        this.name = name;
        this.className = className;
        this.shortName = shortName;
        this.fileName = fileName;
        this.methods = methods;
        this.packageLongName = packageLongName;
    }
}
