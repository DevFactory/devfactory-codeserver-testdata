package com.devfactory.codeserver.api.service;

import com.devfactory.codeserver.api.model.Repository;

import java.util.List;

/**
 * Interface to represent Repository business methods.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public interface RepositoryService {

    List<Repository> findAll();

    Repository find(Integer id);

    Integer save(Repository repo);

    void update(Integer id, Repository repo);

    void update(Repository repo);

    void deleteAll();

    void delete(Integer id);

    List<Repository> findRepositoriesToBeUpdated();

    List<Repository> findRepositoriesToBeCloned();
}
