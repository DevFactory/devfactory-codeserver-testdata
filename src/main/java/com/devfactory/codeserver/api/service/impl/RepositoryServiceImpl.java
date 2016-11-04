package com.devfactory.codeserver.api.service.impl;

import com.devfactory.codeserver.api.dao.RepositoryDAO;
import com.devfactory.codeserver.api.dao.RevisionDAO;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.RepositoryStatus;
import com.devfactory.codeserver.api.service.RepositoryService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Business class to provide repositories.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class RepositoryServiceImpl implements RepositoryService {

    private final RepositoryDAO dao;

    private final RevisionDAO revDao;

    private final ModelMapper mapper;

    @Override
    public List<Repository> findAll() {
        return dao.findAll();
    }

    @Override
    public Repository find(Integer id) {
        return dao.find(id);
    }

    @Override
    public Integer save(Repository repository) {
        Repository fromDB = dao.findAll().stream()
                .filter(repository::equals)
                .findFirst()
                .orElse(null);

        if (fromDB != null) {
            update(fromDB.getId(), repository);
            return fromDB.getId();
        } else {
            return persist(repository);
        }
    }

    private Integer persist(Repository repository) {
        try {
            repository.setStatus(RepositoryStatus.notInitialized());

            return (Integer) dao.save(repository);
        } catch (IOException e) {
            log.error("Save error: {}", e.getMessage(), e);
        }
        return -1;
    }

    @Override
    public void update(Repository repository) {
        update(repository.getId(), repository);
    }

    @Override
    public void update(Integer id, Repository repository) {
        try {
            Repository fromDB = dao.find(id);
            mapper.map(repository, fromDB);
            dao.update(id, fromDB);
        } catch (IOException e) {
            log.error("Update error: {}", e.getMessage(), e);
        }
    }

    @Override
    public void deleteAll() {
        callTry("Delete error", () -> {
            dao.deleteAll();
            revDao.deleteAll();
        });
    }

    @Override
    public void delete(Integer id) {
        callTry("Delete error", () -> {
                    dao.delete(id);
                    revDao.delete(id);
                }
        );
    }

    @Override
    public List<Repository> findRepositoriesToBeUpdated() {
        return dao.findRepositoriesToBeUpdated();
    }

    @Override
    public List<Repository> findRepositoriesToBeCloned() {
        return dao.findRepositoriesToBeCloned();
    }

    @FunctionalInterface
    interface Caller {
        void call() throws IOException;
    }

    private static void callTry(String msg, Caller caller) {
        try {
            caller.call();
        } catch (IOException e) {
            log.error("{}: {}", msg, e.getMessage(), e);
        }
    }
}
