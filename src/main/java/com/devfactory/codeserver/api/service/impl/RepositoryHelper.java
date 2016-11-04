package com.devfactory.codeserver.api.service.impl;

import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.model.Revision;
import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 * Class to handle Scm repository defaults.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Component
public class RepositoryHelper {

    private static final String LOCAL = "local";

    private final CodeServerProperties config;

    public RepositoryHelper(CodeServerProperties config) throws IOException {
        this.config = config;
    }

    String pathToRepository(Integer repoId) {
        return config
                .getRepositoriesBasePath()
                .concat(File.separator)
                .concat(repoId.toString())
                .concat(File.separator)
                .concat(LOCAL);
    }

    public String pathToRepositoryDownloadable(Revision revision) {
        return config
                .getRepositoriesBasePath()
                .concat(File.separator)
                .concat(revision.getRepositoryId().toString())
                .concat(File.separator)
                .concat(revision.getId())
                .concat(".")
                .concat(config.getRepositoriesDownloadableExtension());
    }

}
