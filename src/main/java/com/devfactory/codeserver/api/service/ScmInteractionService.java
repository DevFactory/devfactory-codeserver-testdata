package com.devfactory.codeserver.api.service;

import com.devfactory.codeserver.api.model.Repository;

/**
 * Interface to represent Git business methods.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public interface ScmInteractionService {

    void clone(Repository repository);

    void clone(Repository repository, boolean wait);

    void pull(Repository repository);

    void pull(Repository repository, boolean wait);

}
