package com.devfactory.codeserver.api.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.Data;

/**
 * Temporary class to handle Revision's files. Needs to be removed when DB
 * integration is in place.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Component
public class RevisionDB {

    private final CodeServerProperties config;

    private final DBHelper dbhelper;

    private RevisionsFile dbfile;

    public RevisionDB(
            CodeServerProperties config,
            DBHelper dbhelper) throws IOException {
        this.config = config;
        this.dbhelper = dbhelper;
        loadFromFile();
    }

    public Map<String, Revision> getRevisions() {
        return dbfile.getRevisions();
    }

    public synchronized void save() throws IOException {
        dbhelper.createBaseFolders();
        String json = JsonUtils.toJson(dbfile);
        File file = new File(config.getRevisionsJson());
        FileUtils.writeStringToFile(file, json);
    }

    private synchronized void loadFromFile() throws IOException {
        dbhelper.createBaseFolders();
        try {
            File file = new File(config.getRevisionsJson());
            String json = FileUtils.readFileToString(file);
            dbfile = JsonUtils.fromJson(json, getToken());
        } catch (FileNotFoundException ex) {
            if (dbfile == null) {
                dbfile = new RevisionsFile();
            }
            save();
        }
    }

    private TypeReference<RevisionsFile> getToken() {
        return new TypeReference<RevisionsFile>() {
        };
    }

    public void save(Revision revision) throws IOException {
        dbfile.getRevisions().put(revision.getId(), revision);
        save();
    }

    @Data
    private static class RevisionsFile {

        private Map<String, Revision> revisions = new HashMap<>();

        private RevisionsFile() {
            this.revisions = new HashMap<>();;
        }
    }
}
