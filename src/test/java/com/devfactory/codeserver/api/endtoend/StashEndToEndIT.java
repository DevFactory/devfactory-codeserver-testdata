package com.devfactory.codeserver.api.endtoend;

import static org.junit.Assert.*;
import static java.lang.Thread.sleep;
import com.devfactory.codesdk.model.Entity;
import com.devfactory.codesdk.service.CodesdkService;
import com.devfactory.codeserver.api.BaseIntegrationTest;
import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.CodeServerProperties.ScmCredential;
import com.devfactory.codeserver.api.dto.RepositoryDTO.RepositoryDTOInputSave;
import com.devfactory.codeserver.api.longmethod.ClassDetails;
import com.devfactory.codeserver.api.longmethod.LongMethodService;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.RepositoryCheckoutStatus;
import com.devfactory.codeserver.api.rest.RepositoryController;
import com.devfactory.codeserver.api.service.RepositoryService;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Class to test end to end, from repository creation, git import, integration
 * with SourceAPI.
 *
 * @author Wander Costa
 * @version 1.0
 */
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StashEndToEndIT extends BaseIntegrationTest {
    
    private static Integer id;
    
    @Autowired
    private RepositoryController repoController;
    
    @Autowired
    private RepositoryService repoService;
    
    @Autowired
    private LongMethodService longMethodService;
    
    @Autowired
    private CodesdkService codesdkService;
    
    @Autowired
    private CodeServerProperties config;
    
    @Test
    public void test_100_deleteAll() throws Exception {
        repoController.deleteAll();
    }
    
    @Test
    public void test_110_createRepo() throws Exception {
        log.info("MAKE SURE TO HAVE CONFIGURED CREDENTIALS FOR THE AUTHENTICATED TESTS");
        
        RepositoryDTOInputSave dto = new RepositoryDTOInputSave();
        dto.setName("Stash Project");
        dto.setScmUrl("https://scm.devfactory.com/stash/scm/dfapi/source-api.git?branch=master");
        
        log.info("Creating repository");
        ResponseEntity<Map> resp = repoController.save(dto);
        id = (Integer) resp.getBody().get("id");
        
        assertEquals(HttpStatus.ACCEPTED, resp.getStatusCode());
        assertNotEquals(-1, (int) id);
    }

    /**
     * Wait 30sec (max) until repository changes status to IMPORTED.
     *
     * @throws Exception
     */
    @Test(timeout = 60000)
    public void test_115_waitRepoImported() throws Exception {
        log.info("Waiting repository to be IMPORTED");
        Repository repo;
        do {
            sleep(1000);
            repo = repoService.find(id);
            if (RepositoryCheckoutStatus.IMPORTING_ERROR.equals(repo.getStatus().getCheckoutStatus())) {
                throw new Exception("Error while importing repository");
            }
        } while (!RepositoryCheckoutStatus.IMPORTED.equals(repo.getStatus().getCheckoutStatus()));
    }

    /**
     * Wait 30sec (max) until repository changes status to READY.
     *
     * @throws Exception
     */
    @Test(timeout = 600000)
    public void test_120_waitRepoReady() throws Exception {
        log.info("Waiting repository to be READY");
        Repository repo;
        do {
            sleep(1000);
            repo = repoService.find(id);
            if (RepositoryCheckoutStatus.IMPORTING.equals(repo.getStatus().getCheckoutStatus())) {
                throw new Exception("Error when importing.");
            }
            if (RepositoryCheckoutStatus.IMPORTING_ERROR.equals(repo.getStatus().getCheckoutStatus())) {
                throw new Exception("Error after repository was imported.");
            }
        } while (!RepositoryCheckoutStatus.READY.equals(repo.getStatus().getCheckoutStatus()));
    }

    @Test
    public void test_130_downloadUdb() throws Exception {
        String uri = UriComponentsBuilder
                .fromHttpUrl(
                        config.getSourceapi()
                        .getRest()
                        .getMetadata()
                        .getDownloadCodedb()
                        .replaceAll("\\{projectId\\}", id.toString()))
                .build(true)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        // NOT YET FINISHED. ZIP FILE IS EMPTY.
        ResponseEntity<byte[]> response = restTemplate.getForEntity(uri, byte[].class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            FileOutputStream output = new FileOutputStream(new File("/tmp/" + id.toString() + ".zip"));
            IOUtils.write(response.getBody(), output);
        }
    }

    /**
     * Run example.
     *
     * @throws Exception
     */
    @Test
    public void test_140_runLongMethod() throws Exception {
        log.info("Running example");
        String uid = repoService.find(id).getHeadRevision();
        List<ClassDetails> list = longMethodService.longMethod(uid);
        assertNotNull(list);
    }
    
}
