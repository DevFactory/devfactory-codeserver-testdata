package com.devfactory.codeserver.api.service.impl;

import com.devfactory.codeserver.api.exception.ApiException;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.IntegrationService;
import com.devfactory.codeserver.api.service.RepositoryService;
import com.devfactory.codeserver.api.service.RevisionService;
import com.devfactory.codeserver.api.service.SourceApiService;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Business class to provide methods to integrate with the background
 * applications.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

    private final RepositoryService repositoryService;

    private final RevisionService revisionService;

    private final SourceApiService sourceApiService;

    @Override
    public void integrate(Repository repository, Revision revision) throws ApiException {
        try {
            revision.analyzing();
            revisionService.save(repository.getId(), revision);

            sourceApiService.importFiles(repository, revision);

            revision.analyzed();
            repository.makeReady();
        } catch (IOException | URISyntaxException ex) {
            log.debug("Error while integrating with SourceApi", ex);
            revision.failed("Error while integrating with SourceApi: " + ex.getMessage());
        } finally {
            revisionService.save(repository.getId(), revision);
            repositoryService.update(repository);
        }
    }

}
