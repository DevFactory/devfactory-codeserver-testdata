package com.devfactory.codeserver.api.service.impl;

import com.devfactory.codeserver.api.exception.ApiException;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.IntegrationService;
import com.devfactory.codeserver.api.service.RepositoryService;
import com.devfactory.codeserver.api.service.RevisionService;
import com.devfactory.codeserver.api.service.ScmInteractionService;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to schedule scm clone / update for the active repositories.
 */
@Slf4j
@Service
@AllArgsConstructor
@EnableScheduling
public class ScmInteractionScheduler {

    private final RepositoryService repositoryService;

    private final RevisionService revisionService;

    private final ScmInteractionService scmService;

    private final IntegrationService integrationService;

    @Scheduled(fixedRate = 10000, initialDelay = 1000)
    public void cloneNewRepositories() throws IOException {
        repositoryService.findRepositoriesToBeCloned().forEach(this::clone);
    }

    @Scheduled(fixedRate = 30000, initialDelay = 1000)
    public void updateRepositories() throws IOException {
        log.debug("Checking repositories...");
        repositoryService.findRepositoriesToBeUpdated().forEach(this::pull);
    }

    @Scheduled(fixedRate = 60000, initialDelay = 1000)
    public void runRevisions() throws IOException {
        log.debug("Checking repositories...");
        revisionService.findRevisionsToRun().forEach(this::runAnalyze);
    }


    @Scheduled(fixedRate = 20000, initialDelay = 10000)
    public void timeoutRevisions() throws IOException {
        log.debug("Checking repositories...");
        revisionService.findAllInProgessRevisions().forEach(this::timeOut);
    }

    private void timeOut(Revision revision) {
        int timeoutInSec = 5 * 60;
        ZonedDateTime timeOutTime = ZonedDateTime.now().minusSeconds(timeoutInSec);
        if (revision.getLastUpdated().isBefore(timeOutTime)){
            revision.failed( "Analyzing timed out after " + timeoutInSec + " seconds");
            try {
                revisionService.save(revision.getRepositoryId(), revision);
            } catch (ApiException e) {
                log.error("Unable to timeout revision {}. Error: {} ", revision.getId(), e.getMessage(), e);
            }
        }
    }

    private void pull(Repository repository) {
        scmService.pull(repository);
    }

    private void clone(Repository repository) {
        scmService.clone(repository);
    }

    private void runAnalyze(Revision revision) {
        Repository repository = repositoryService.find(revision.getRepositoryId());
        try {
            integrationService.integrate(repository, revision);
        } catch (Exception e) {
            log.error("Not able to integrate {} ", e.getMessage(), e);
        }
    }
}
