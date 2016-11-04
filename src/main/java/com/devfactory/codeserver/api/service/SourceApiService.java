package com.devfactory.codeserver.api.service;

import com.devfactory.codeserver.api.exception.ApiException;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.Revision;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * Interface to represent business methods to interact with source-api.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public interface SourceApiService {
    
    String HEALTH_OK = "UP";
    String HEALTH_NOT_OK = "DOWN";

    void importFiles(Repository repository, Revision revision) throws ApiException, IOException, URISyntaxException;

    boolean isHealthOk();

    Path downloadCodedb(Revision revision) throws IOException;
    
}
