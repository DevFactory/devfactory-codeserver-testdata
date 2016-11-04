package com.devfactory.codeserver.api.service;

import com.devfactory.codeserver.api.exception.ApiException;
import com.devfactory.codeserver.api.model.Revision;

import java.util.List;

/**
 * Interface to represent Revision business methods.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public interface RevisionService {

    List<Revision> findAll(Integer repoId) throws Exception;

    Revision find(Integer repoId, Integer id) throws Exception;

    String save(Integer repoId, Revision revision) throws ApiException;

    void deleteAll(Integer repoId) throws Exception;
    
    void deleteAll() throws Exception;

    List<Revision> findRevisionsToRun();

    Revision findRevisionByRepositoryIdAndScmRevId(Integer repoId, String rev) throws ApiException;

    List<Revision> findAllInProgessRevisions();

    Revision findRevision(String revId);
}
