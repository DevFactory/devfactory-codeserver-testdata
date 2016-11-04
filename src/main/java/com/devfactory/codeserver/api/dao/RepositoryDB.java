package com.devfactory.codeserver.api.dao;

import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * Temporary class to handle Repository's files. Needs to be removed when DB
 * integration is in place.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Component
public class RepositoryDB {

    private final CodeServerProperties config;

    private RepositoriesFile repoFile;

    public RepositoryDB(CodeServerProperties config) throws IOException {
        this.config = config;
        loadFromFile();
    }

    public int newId() {
        repoFile.setLastId(repoFile.getLastId() + 1);
        return repoFile.getLastId();
    }

    public Map<Integer, Repository> getRepositories() {
        return repoFile.getRepositories();
    }

    public synchronized void save() throws IOException {
        createBaseFolder();
        String json = JsonUtils.toJson(repoFile);
        File file = new File(config.getRepositoriesJson());
        FileUtils.writeStringToFile(file, json);
    }

    private synchronized void loadFromFile() throws IOException {
        createBaseFolder();
        try {
            File file = new File(config.getRepositoriesJson());
            String json = FileUtils.readFileToString(file);
            repoFile = JsonUtils.fromJson(json, getToken());
        } catch (FileNotFoundException ex) {
            if (repoFile == null) {
                repoFile = new RepositoriesFile();
            }
            save();
        }
    }

    private synchronized void createBaseFolder() throws IOException {
        if (!new File(config.getBasePath()).exists()) {
            FileUtils.forceMkdir(new File(config.getBasePath()));
        }
        if (!new File(config.getRepositoriesBasePath()).exists()) {
            FileUtils.forceMkdir(new File(config.getRepositoriesBasePath()));
        }
    }

    private TypeReference<RepositoriesFile> getToken() {
        return new TypeReference<RepositoriesFile>() {
        };
    }

    public void save(Repository repository) throws IOException {
        if (repository.getId() == null) {
            repository.setId(newId());
        }
        repoFile.getRepositories().put(repository.getId(), repository);
        save();
    }

    @Data
    private static class RepositoriesFile {

        private int lastId;

        private Map<Integer, Repository> repositories;

        private RepositoriesFile() {
            this.lastId = 0;
            this.repositories = new HashMap<>();
        }

    }

}
