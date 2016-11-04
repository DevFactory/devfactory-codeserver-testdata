package com.devfactory.codeserver.api.service;

import com.devfactory.codeserver.api.exception.ApiException;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.Revision;

/**
 * Interface to represent integration service.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public interface IntegrationService {

    void integrate(Repository repository, Revision revision) throws ApiException;

}
