package com.devfactory.codeserver.api.service.impl;

import com.devfactory.codeserver.api.BaseIntegrationTest;
import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.CodeServerProperties.Rest;
import com.devfactory.codeserver.api.CodeServerProperties.SourceApi;
import com.devfactory.codeserver.api.EntityFactory;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.IntegrationService;
import com.devfactory.codeserver.api.service.RepositoryService;
import com.devfactory.codeserver.api.service.RevisionService;
import com.devfactory.codeserver.api.service.ScmInteractionService;
import com.devfactory.codeserver.api.sourceapi.CodesdkDb;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Class to test service implementation.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class SourceApiServiceImplIT extends BaseIntegrationTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RevisionService revisionService;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private ScmInteractionService scmInteractionService;

    @Autowired
    private SourceApiServiceImpl service;

    @Autowired
    private DownloadableServiceImpl downloadableService;

    @Autowired
    private CodesdkDb sourceApi;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void revisionAnalyzing() throws Exception {
        Repository repository = EntityFactory.newLongMethodScmRepo();
        Integer repoId = repositoryService.save(repository);

        repository = repositoryService.find(repoId);

        scmInteractionService.clone(repository, true);

        List<Revision> revisions = revisionService.findAll(repoId);

        assertEquals(1, revisions.size());

        Revision revision = revisions.get(0);

        integrationService.integrate(repository, revision);

        Path codedbFile = service.downloadCodedb(revision);

        assertThat(codedbFile.toFile().getAbsolutePath(), CoreMatchers.containsString(revision.getId()));
    }

    @Test
    public void testHealthOk() {
        assertTrue(service.isHealthOk());
    }

    @Test
    public void testHealthNotOk() {
        CodeServerProperties configMock = Mockito.mock(CodeServerProperties.class);
        SourceApi sourceApiConfig = Mockito.mock(SourceApi.class);
        Rest rest = Mockito.mock(Rest.class);
        when(configMock.getSourceapi()).thenReturn(sourceApiConfig);
        when(sourceApiConfig.getRest()).thenReturn(rest);
        when(rest.getHealthCheckUrl()).thenReturn("http://localhost:1212");
        SourceApiServiceImpl mockedService = new SourceApiServiceImpl(
                downloadableService,
                sourceApi,
                configMock,
                restTemplate);
        assertFalse(mockedService.isHealthOk());
    }

}
