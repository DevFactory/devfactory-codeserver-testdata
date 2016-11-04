package com.devfactory.codeserver.api.rest;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.devfactory.codeserver.api.BaseIntegrationTest;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yogesh.badke@aurea.com
 */
@Slf4j
public class RepositoryControllerIT extends BaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private final JsonNodeFactory jsonFactory = JsonNodeFactory.instance;

    @Test
    public void testPostRepositories() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(getRequestJson(), headers);
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> postForEntity = restTemplate.postForEntity("/api/repositories", entity, Map.class);
        HttpStatus statusCode = postForEntity.getStatusCode();
        assertTrue(HttpStatus.ACCEPTED.equals(statusCode));
        assertTrue(postForEntity.getBody().containsKey("id"));
    }

    private String getRequestJson() {
        ObjectNode objectNode = jsonFactory.objectNode();
        objectNode.put("name", UUID.randomUUID().toString());
        objectNode.put("scm_url", "https://github.com/SquareSquash/java.git");
        ZonedDateTime date = ZonedDateTime.now();
        date.format(DateTimeFormatter.ISO_DATE_TIME);
        objectNode.put("valid_date", date.toString());
        return objectNode.toString();
    }
}
