package com.devfactory.codeserver.api.service.impl;

import static org.mockito.Mockito.*;
import com.devfactory.codeserver.api.BaseMockUnitTest;
import com.devfactory.codeserver.api.MockUnitTestConfig;
import com.devfactory.codeserver.api.model.Repository;
import java.util.Arrays;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ScmInteractionSchedulerTest extends BaseMockUnitTest {

    @Autowired
    private MockUnitTestConfig config;

    @Autowired
    private ScmInteractionScheduler scheduler;

    @Test
    public void updateRepositories() throws Exception {
        Repository r1 = new Repository();
        Repository r2 = new Repository();
        when(config.getRepositoryService().findRepositoriesToBeUpdated()).thenReturn(Arrays.asList(r1, r2));

        scheduler.updateRepositories();

        verify(config.getRepositoryService(), atLeast(1)).findRepositoriesToBeUpdated();
        verify(config.getScmInteractionService(), atLeast(1)).pull(r1);
        verify(config.getScmInteractionService(), atLeast(1)).pull(r2);
    }

    @Test
    public void cloneNewRepositories() throws Exception {
        Repository r1 = new Repository();
        Repository r2 = new Repository();
        when(config.getRepositoryService().findRepositoriesToBeCloned()).thenReturn(Arrays.asList(r1, r2));

        scheduler.cloneNewRepositories();

        verify(config.getRepositoryService(), atLeast(1)).findRepositoriesToBeCloned();
        verify(config.getScmInteractionService(), atLeast(1)).clone(r1);
        verify(config.getScmInteractionService(), atLeast(1)).clone(r2);
    }

}
