package com.devfactory.codeserver.api.dao;

import com.devfactory.codeserver.api.exception.ApiException;
import com.devfactory.codeserver.api.exception.ErrorCode;
import com.devfactory.codeserver.api.model.Revision;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

/**
 * Class to represent Repositories' DAO.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class RevisionDAO implements DAO<Revision> {

    private final RevisionDB db;

    @Override
    public List<Revision> findAll() {
        return new ArrayList<>(db.getRevisions().values());
    }

    @Override
    public Revision find(Serializable id) {
        return db.getRevisions().get(id);
    }

    @Override
    public Serializable save(Revision revision) throws IOException {
        db.save(revision);
        return revision.getId();
    }

    @Override
    public void update(Serializable id, Revision e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteAll() throws IOException {
        db.getRevisions().clear();
        db.save();
    }

    @Override
    public void delete(Serializable repoId) throws IOException {
        findAll()
                .stream()
                .filter((r) -> (Objects.equals(r.getRepositoryId(), repoId)))
                .findFirst()
                .ifPresent((r) -> db.getRevisions().remove(r.getId()));
        db.save();
    }

    public List<Revision> findRevisionsToRun() {
        return findAll().stream().filter(Revision::canRun).collect(Collectors.toList());
    }

    public Revision findRevisionByRepositoryIdAndScmRevId(Integer repoId, String rev) throws ApiException {
        return findAll().stream().filter(
                (r) -> repoId.equals(r.getRepositoryId()) && rev.equalsIgnoreCase(r.getScmRev()))
                .findFirst()
                .orElseThrow(() -> new ApiException(String.format("Unknown scm revision '%s' for repository %d", rev, repoId), ErrorCode.UNKNOWN_SCM_REVISION));
    }

    public List<Revision> findAllInProgessRevisions() {
        return findAll().stream().filter(Revision::isInProgress).collect(Collectors.toList());
    }
}
