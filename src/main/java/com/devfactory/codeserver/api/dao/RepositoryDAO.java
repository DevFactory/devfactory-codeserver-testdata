package com.devfactory.codeserver.api.dao;

import com.devfactory.codeserver.api.model.Repository;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class to represent Repositories' DAO.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class RepositoryDAO implements DAO<Repository> {

    private final RepositoryDB db;

    @Override
    public List<Repository> findAll() {
        return new ArrayList<>(db.getRepositories().values());
    }

    public List<Repository> findRepositoriesToBeUpdated() {
        return findAll().stream().filter(Repository::canBeUpdated).collect(Collectors.toList());
    }

    public List<Repository> findRepositoriesToBeCloned() {
        return findAll().stream().filter(Repository::canBeCloned).collect(Collectors.toList());
    }

    @Override
    public Repository find(Serializable id) {
        return db.getRepositories().get(id);
    }

    @Override
    public Serializable save(Repository repository) throws IOException {
        db.save(repository);
        return repository.getId();
    }

    @Override
    public void update(Serializable id, Repository repository) throws IOException {
        repository.setId((Integer) id);
        db.save(repository);
    }

    @Override
    public void deleteAll() throws IOException {
        db.getRepositories().clear();
        db.save();
    }

    @Override
    public void delete(Serializable id) throws IOException {
        db.getRepositories().remove(id);
        db.save();
    }
}
