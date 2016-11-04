package com.devfactory.codeserver.api.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.modelmapper.internal.util.Lists;
import org.springframework.stereotype.Service;

import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.CodeServerProperties.ScmCredential;
import com.devfactory.codeserver.api.exception.ApiException;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.RepositoryCheckoutStatus;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.RepositoryService;
import com.devfactory.codeserver.api.service.RevisionService;
import com.devfactory.codeserver.api.service.ScmInteractionService;
import com.devfactory.codeserver.api.util.ZipUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Business class to provide Git.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class ScmInteractionServiceImpl implements ScmInteractionService {

    private final RepositoryService repositoryService;

    private final RevisionService revisionService;

    private final RepositoryHelper repositoryHelper;

    private final CodeServerProperties config;

    /**
     * Call the cloning of the scm repository. It starts a new thread to be
     * executed in non-blocking mode. The repository status is updated when
     * concluded/failed.
     *
     * @param repository The scm repository.
     */
    @Override
    public void clone(Repository repository) {
        clone(repository, false);
    }

    /**
     * Call the cloning of the git repository. It starts a new thread to be
     * executed in non-blocking mode. The repository status is updated when
     * concluded/failed.
     *
     * @param repository The scm repository.
     * @param wait Waits until finished.
     */
    @Override
    public void clone(Repository repository, boolean wait) {
        if (wait) {
            runClone(repository);
        } else {
            new Thread(() -> runClone(repository)).start();
        }
    }

    /**
     * Update the files of a git repository. It starts a new thread to be
     * executed in non-blocking mode. The repository status is updated when
     * concluded/failed.
     *
     * @param repository The scm repository.
     */
    @Override
    public void pull(Repository repository) {
        log.debug("Pull request on {}", repository.getName());
        pull(repository, false);
    }

    /**
     * Update the files of a git repository. It starts a new thread to be
     * executed in non-blocking mode. The repository status is updated when
     * concluded/failed.
     *
     * @param repository The scm repository.
     * @param wait Waits until finished.
     */
    @Override
    public void pull(Repository repository, boolean wait) {
        if (wait) {
            runPull(repository);
        } else {
            new Thread(() -> runPull(repository)).start();
        }
    }

    /**
     * Method to be run clone inside the thread.
     *
     * @param repository The scm repository to be cloned.
     */
    private void runClone(Repository repository) {
        try {
            log.debug("Cloning {}", repository.getName());

            repository.importing();
            repositoryService.update(repository);

            Path folder = Paths.get(repositoryHelper.pathToRepository(repository.getId()));
            log.trace("Using path {}", folder);
            FileUtils.deleteDirectory(folder.toFile());
            Files.createDirectories(folder);

            /**
             * Choose the first matching credentials.
             */
            ScmCredential scmCredentials = findBestScmCredentials(config.getScmConfigs(), new URL(repository.getScmUrl()));

            CloneCommand clone = Git.cloneRepository()
                    .setURI(repository.getShortUrl())
                    .setBranch(repository.getBranch())
                    .setDirectory(folder.toFile())
                    .setCredentialsProvider(findCredentialsProvider(scmCredentials));

            Git git = clone.call();

            repository.makeImported();
            repositoryService.update(repository);

            prepareRevision(repository, git);

        } catch (GitAPIException | ApiException | IOException ex) {
            log.error("Cloning error", ex);
            repository.initializeError("Error while cloning git repository: " + ex.getMessage());
            repositoryService.update(repository);
        }
    }

    private void prepareRevision(Repository repository, Git git) throws GitAPIException, IOException, ApiException {

        RevCommit rev = git.log().call().iterator().next(); //last revisiom

        String latestRevId = rev.getName();
        Date commitDate = rev.getAuthorIdent().getWhen();

        //Current date format expects zone to be part of serialized string
        //LocalDateTime doesn't have tomezone info. Hence using ZonedDateTime 
        ZonedDateTime zdt = ZonedDateTime.ofInstant(commitDate.toInstant(), ZoneId.systemDefault());

        Revision revision = new Revision(repository.getId(), latestRevId, zdt);

        prepareZip(repository, revision);

        revisionService.save(repository.getId(), revision);
    }

    public File prepareZip(Repository repository, Revision revision) throws IOException {
        File zip = new File(repositoryHelper.pathToRepositoryDownloadable(revision));
        ZipUtils.zip(repositoryHelper.pathToRepository(repository.getId()), zip);
        return zip;
    }

    /**
     * Method to run fetch & pull inside a thread.
     *
     * @param repository The repository.
     */
    private void runPull(Repository repository) {

        try {
            RepositoryCheckoutStatus prevState = repository.getStatus().getCheckoutStatus();
            File repositoryFolder = Paths.get(repositoryHelper.pathToRepository(repository.getId())).toFile();
            Git git = Git.open(repositoryFolder);

            /**
             * Choose the first matching credentials.
             */
            ScmCredential scmCredentials = findBestScmCredentials(config.getScmConfigs(), new URL(repository.getScmUrl()));

            long commits = Lists
                    .from(git.log().call().iterator())
                    .stream()
                    .count();
            Git.open(repositoryFolder)
                    .pull()
                    .setCredentialsProvider(findCredentialsProvider(scmCredentials))
                    .call();
            commits = Lists
                    .from(git.log().call().iterator())
                    .stream()
                    .count() - commits;

            LocalDateTime now = LocalDateTime.now();

            if (commits == 0) {
                //TODO do we need this update?
                if (prevState != RepositoryCheckoutStatus.READY) {
                    repository.makeImported();
                }
                repositoryService.update(repository);
            } else if (commits > 0) {
                repository.importing(now, (int) commits);
                repositoryService.update(repository);

                prepareRevision(repository, git);
            }
        } catch (GitAPIException | ApiException | IOException ex) {
            repository.importError("Error while pulling git repository: " + ex.getMessage());
            repositoryService.update(repository);
        }
    }

    /**
     * Returns the CredentialsProvider for the ScmCredential.
     *
     * @param scmCred the ScmCredential
     * @return the CredentialsProvider for the ScmCredential.
     */
    private CredentialsProvider findCredentialsProvider(ScmCredential scmCred) {
        return scmCred != null
                ? new UsernamePasswordCredentialsProvider(scmCred.getUserName(), scmCred.getPassword())
                : null;
    }

    /**
     * Returns the first match for credentials.
     *
     * @param scmUrl The git URL to be matched.
     * @return the first match for credentials.
     */
    private ScmCredential findBestScmCredentials(List<ScmCredential> creds, URL scmUrl) throws MalformedURLException {
        if (creds == null || scmUrl == null) {
            return null;
        }

        return creds
                .stream()
                .filter(cred -> startsWith(scmUrl, cred.getHost()))
                .findFirst()
                .orElse(null);
    }

    private boolean startsWith(URL inputUrl, URL configuredHost) {
        String inputUrlString = inputUrl.toString();
        String configuredHostString = configuredHost.toString();
        return inputUrlString.length() < configuredHostString.length()
                ? configuredHostString.startsWith(inputUrlString)
                : inputUrlString.startsWith(configuredHostString);
    }

}
