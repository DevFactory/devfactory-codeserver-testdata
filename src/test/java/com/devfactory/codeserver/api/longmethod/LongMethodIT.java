package com.devfactory.codeserver.api.longmethod;

import com.devfactory.codeserver.api.BaseIntegrationTest;
import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.EntityFactory;
import com.devfactory.codeserver.api.sourceapi.CodesdkDb;
import com.devfactory.codeserver.api.sourceapi.CodesdkRestParams;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class LongMethodIT extends BaseIntegrationTest {

    @Autowired
    private CodeServerProperties config;

    @Autowired
    private CodesdkDb sourceapi;

    @Autowired
    private LongMethodService service;

    @Test
    public void testLongMethod() throws Throwable {
        String uid = UUID.randomUUID().toString();
        String uri = UriComponentsBuilder
                .fromHttpUrl(
                            config.getSourceapi()
                              .getRest()
                                    .getMetadata()
                                    .getGenerateViaUpload()
                              .replaceAll("\\{projectId\\}", uid)
                            )
                .build(true)
                .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        CodesdkRestParams params = new CodesdkRestParams(uri, restTemplate);
        sourceapi.generateWithUpload(
                "Java",
                EntityFactory.getLongMethodFiles(),
                params);
        List<ClassDetails> list = service.longMethod(uid);

        assertEquals(1, list.size());
        assertEquals("LongMethodTestClass", list.get(0).getClassName());
        assertEquals(1, list.get(0).getMethods().size());
        assertEquals("long_method", list.get(0).getMethods().get(0).getAlgo().getName());
        assertEquals(8, list.get(0).getMethods().get(0).getBeginLine());
        assertEquals(45, list.get(0).getMethods().get(0).getCountLineCode());
        assertEquals(44, list.get(0).getMethods().get(0).getEndLine());
        assertEquals(100, list.get(0).getMethods().get(0).getMethodLengthMaxThreshold());
        assertEquals(30, list.get(0).getMethods().get(0).getMethodLengthMinThreshold());
        assertEquals("LongMethodTestClass.java", list.get(0).getName());
        assertEquals("longmethod.java.base_case", list.get(0).getPackageLongName());
        assertEquals("LongMethodTestClass.java", list.get(0).getShortName());
    }

}
