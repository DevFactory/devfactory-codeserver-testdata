package com.devfactory.codeserver.api.service.impl;

import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.DownloadableService;
import java.io.File;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Business class to provide Git.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class DownloadableServiceImpl implements DownloadableService {

    private final RepositoryHelper repositoryHelper;

    @Override
    public File zipFileLocation(Revision revision) {
        return new File(repositoryHelper.pathToRepositoryDownloadable(revision));
    }

}
