package com.devfactory.codeserver.api.service.impl;

import com.devfactory.codeserver.api.dao.RepositoryDAO;
import com.devfactory.codeserver.api.dao.RevisionDAO;
import com.devfactory.codeserver.api.exception.ApiException;
import com.devfactory.codeserver.api.exception.ErrorCode;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.RevisionService;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Business class to provide revisions.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class RevisionServiceImpl implements RevisionService {

    private final RevisionDAO dao;

    private final RepositoryDAO repoDao;

    @Override
    public List<Revision> findAll(Integer repoId) throws Exception {
        if (repoDao.find(repoId) == null) {
            throw new ApiException("Repository does not exist", ErrorCode.REPOSITORY_DOES_NOT_EXIST);
        }
        return dao.findAll()
                .stream()
                .filter((r) -> Objects.equals(r.getRepositoryId(), repoId))
                .collect(Collectors.toList());
    }

    @Override
    public Revision find(Integer repoId, Integer id) throws Exception {
        if (repoDao.find(repoId) == null) {
            throw new ApiException("Repository does not exist", ErrorCode.REPOSITORY_DOES_NOT_EXIST);
        }
        return dao.find(id);
    }

    @Override
    public String save(Integer repoId, Revision revision) throws ApiException {
        Repository repo = repoDao.find(repoId);
        if (repo == null) {
            throw new ApiException("Repository does not exist", ErrorCode.REPOSITORY_DOES_NOT_EXIST);
        }

        try {
            Revision revDb = dao.find(revision.getId());
            if (revDb != null) {
                dao.delete(revision.getId());
            }
            revision.setRepositoryId(repoId);
            dao.save(revision);

            repo.setHeadRevision(revision.getId());

            repoDao.update(repoId, repo);
        } catch (IOException e) {
            log.error("Update error {} ", e.getMessage(), e);
        }

        return revision.getId();
    }

    @Override
    public void deleteAll(Integer repoId) throws Exception {
        List<Revision> toDelete = dao.findAll()
                .stream()
                .filter((r) -> Objects.equals(r.getRepositoryId(), repoId))
                .collect(Collectors.toList());
        for (Revision rev : toDelete) {
            dao.delete(rev.getId());
        }
    }

    @Override
    public void deleteAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Revision> findRevisionsToRun() {
        return dao.findRevisionsToRun();
    }

    @Override
    public Revision findRevisionByRepositoryIdAndScmRevId(Integer repoId, String rev) throws ApiException {
        return dao.findRevisionByRepositoryIdAndScmRevId(repoId, rev);
    }

    @Override
    public List<Revision> findAllInProgessRevisions() {
        return dao.findAllInProgessRevisions();
    }

    @Override
    public Revision findRevision(String revId) {
        return dao.find(revId);
    }
}
