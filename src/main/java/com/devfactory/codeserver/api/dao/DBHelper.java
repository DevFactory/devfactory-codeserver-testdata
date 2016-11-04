package com.devfactory.codeserver.api.dao;

import com.devfactory.codeserver.api.CodeServerProperties;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Class to provide DB persistence utility.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Component
@AllArgsConstructor
public class DBHelper {

    private final CodeServerProperties config;

    public synchronized void createBaseFolders() throws IOException {
        Path basePath = Paths.get(config.getBasePath());
        if (!Files.exists(basePath)) {
            Files.createDirectories(basePath);
        }

        Path repositoriesBasePath = Paths.get(config.getRepositoriesBasePath());
        if (!Files.exists(repositoriesBasePath)) {
            Files.createDirectories(repositoriesBasePath);
        }

        Path revisionsBasePath = Paths.get(config.getRevisionsBasePath());
        if (!Files.exists(revisionsBasePath)) {
            Files.createDirectories(revisionsBasePath);
        }
    }

}
