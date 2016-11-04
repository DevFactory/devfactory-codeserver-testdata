package com.devfactory.codeserver.api.service.impl;

import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.exception.ApiException;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.DownloadableService;
import com.devfactory.codeserver.api.service.SourceApiService;
import com.devfactory.codeserver.api.sourceapi.CodesdkDb;
import com.devfactory.codeserver.api.sourceapi.CodesdkRestParams;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Business class to provide methods to interact with source-api.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Service
@AllArgsConstructor
@Slf4j
public class SourceApiServiceImpl implements SourceApiService {

    private final DownloadableService downloadableService;

    private final CodesdkDb sourceapi;

    private final CodeServerProperties config;

    private final RestTemplate restTemplate;

    @Override
    public void importFiles(Repository repository, Revision revision) throws ApiException, IOException, URISyntaxException {
        File file = downloadableService.zipFileLocation(revision);
        String uid = revision.getId();

        String uri = UriComponentsBuilder
                .fromHttpUrl(
                        config.getSourceapi()
                        .getRest()
                        .getMetadata()
                        .getGenerateViaUpload()
                        .replaceAll("\\{projectId\\}", uid))
                .build(true)
                .toUriString();

        generateWithUpload(
                uri,
                repository.getLanguage(),
                Collections.singletonList(file));
    }

    private void generateWithUpload(String uri, String language, List<File> files) throws ApiException {
        CodesdkRestParams params = new CodesdkRestParams(uri, restTemplate);
        sourceapi.generateWithUpload(
                language,
                files,
                params);
    }

    @Override
    public Path downloadCodedb(Revision revision) throws IOException {

        Path outFile = Files.createTempFile(revision.getId() + "-rev", ".zip");

        String uri = UriComponentsBuilder
                .fromHttpUrl(
                        config.getSourceapi()
                        .getRest()
                        .getMetadata()
                        .getDownloadCodedb()
                        .replaceAll("\\{projectId\\}", revision.getId()))
                .build(true)
                .encode()
                .toUriString();

        RequestCallback requestCallback = request -> request.getHeaders()
                .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));

        ResponseExtractor<Void> responseExtractor = response -> {
            Files.copy(response.getBody(), outFile, StandardCopyOption.REPLACE_EXISTING);
            return null;
        };

        restTemplate.execute(uri, HttpMethod.GET, requestCallback, responseExtractor);

        return outFile;
    }

    @Override
    public boolean isHealthOk() {
        String healthCheckUrl = config.getSourceapi().getRest().getHealthCheckUrl();
        log.debug("Checking health of source api on url [{}]", healthCheckUrl);
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> body = restTemplate.getForEntity(healthCheckUrl, Map.class).getBody();
            return HEALTH_OK.equals(body.get("status"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

}
