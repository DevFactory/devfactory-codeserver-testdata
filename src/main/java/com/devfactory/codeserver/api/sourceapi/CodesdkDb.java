package com.devfactory.codeserver.api.sourceapi;

import java.io.File;
import java.util.List;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CodesdkDb {

    public String generateWithUpload(
            String language,
            List<File> filesFromDir,
            CodesdkRestParams params) {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        for (File file : filesFromDir) {
            map.add("files", new FileSystemResource(file));
        }
        map.add("language", language);
        log.debug("Using {}", params.getUrl());

        ResponseEntity<ObjectNode> entityResponse = params.getRestTemplate().postForEntity(
                params.getUrl(),
                insertMultipartHeaders(map),
                ObjectNode.class);
        ObjectNode body = entityResponse.getBody();
        return body.get("id").asText();
    }

    private HttpEntity<LinkedMultiValueMap<String, Object>> insertMultipartHeaders(
            LinkedMultiValueMap<String, Object> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return new HttpEntity<>(map, headers);
    }

}
