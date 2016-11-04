package com.devfactory.codeserver.api.longmethod;

import static com.devfactory.codeserver.api.longmethod.AntipatternAlgo.LONG_METHOD;
import com.devfactory.codesdk.exception.CodesdkException;
import com.devfactory.codesdk.model.Entity;
import com.devfactory.codesdk.model.Reference;
import com.devfactory.codesdk.service.CodesdkService;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class LongMethodService {

    public static final int METHOD_MIN_THRESHOLD = 30;
    public static final int METHOD_MAX_THRESHOLD = 100;

    @Autowired
    private CodesdkService codesdkService;

    public List<ClassDetails> longMethod(String uid) throws Exception {
        List<Entity> packageEntities = codesdkService.ents(uid, "Package ~unused ~unresolved ~unknown");
        List<ClassDetails> globallyViolatedClasses = new ArrayList<>();
        for (Entity pkg : packageEntities) {
            List<Reference> classReferences = codesdkService.refs(uid, pkg.getUniquename(), "~unresolved ~unknown", "Class", true);
            for (Reference classReference : classReferences) {
                Entity classFile = classReference.getFile();
                Entity classEntity = classReference.getEntity();
                List<Reference> methodReferences = codesdkService.refs(uid, classEntity.getUniquename(), "Define", "Method", true);
                List<MethodDetails> violations = processMethods(uid, pkg.getLongname(), classFile, methodReferences);
                if (!violations.isEmpty()) {
                    String name = FilenameUtils.getName(classFile.getLongname());
                    ClassDetails details = new ClassDetails(classEntity.getId(), name, classEntity.getSimplename(), name, classFile.getLongname(), violations, pkg.getLongname());
                    globallyViolatedClasses.add(details);
                }
            }
        }
        return globallyViolatedClasses;
    }

    private List<MethodDetails> processMethods(
            String metadataId,
            String pkgName,
            Entity classFile,
            List<Reference> methods) throws CodesdkException {
        final List<MethodDetails> antiPatternNodesList = new ArrayList<>();
        for (final Reference method : methods) {
            Entity methodEntity = method.getEntity();
            List<Reference> endRefs = codesdkService.refs(metadataId, methodEntity.getUniquename(), "End", "", true);
            if (endRefs == null || endRefs.isEmpty()) {
                continue;
            }
            final int beginLine = method.getLine();
            final int endLine = endRefs.get(0).getLine();
            final int methodLength = endLine - beginLine + 1;
            if (methodLength > METHOD_MIN_THRESHOLD) {
                final MethodDetails antiPatternNode = new MethodDetails(LONG_METHOD);
                antiPatternNode.setMethodLengthMinThreshold(METHOD_MIN_THRESHOLD);
                antiPatternNode.setMethodLengthMaxThreshold(METHOD_MAX_THRESHOLD);
                antiPatternNode.setPriority(methodLength);
                antiPatternNode.setRelFile(classFile.getSimplename());
                antiPatternNode.setFile(classFile.getLongname());
                antiPatternNode.setBeginLine(beginLine);
                antiPatternNode.setEndLine(endLine);
                antiPatternNode.setPkg(pkgName);
                antiPatternNode.setMethodSignature(getMethodSignature(method, metadataId));
                double metric = codesdkService.metric(metadataId, classFile.getUniquename(), "CountLine");
                antiPatternNode.setCountLineCode((int) metric);
                antiPatternNode.setId(new Long(methodEntity.getId()));
                antiPatternNodesList.add(antiPatternNode);
            }
        }
        return antiPatternNodesList;
    }

    private String getMethodSignature(Reference method, String metadataId) throws CodesdkException {
        Entity methodEntity = method.getEntity();
        List<Reference> params = codesdkService.refs(metadataId, methodEntity.getUniquename(), "", "Parameter ~Catch ~Type", true);
        final String methodName = methodEntity.getSimplename();
        final StringBuilder args = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            args.append(params.get(i).getEntity().getType());
            if (i + 1 < params.size()) {
                args.append(", ");
            }
        }
        return methodName + " (" + args + ") ";
    }

}
