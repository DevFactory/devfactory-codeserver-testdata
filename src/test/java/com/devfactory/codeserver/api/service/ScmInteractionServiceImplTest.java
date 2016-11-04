package com.devfactory.codeserver.api.service;

import com.devfactory.codeserver.api.BaseMockUnitTest;
import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.CodeServerProperties.ScmCredential;
import com.devfactory.codeserver.api.EntityFactory;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.RepositoryCheckoutStatus;
import com.devfactory.codeserver.api.model.Revision;
import com.devfactory.codeserver.api.service.impl.ScmInteractionServiceImpl;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class ScmInteractionServiceImplTest extends BaseMockUnitTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RevisionService revisionService;

    @Autowired
    private ScmInteractionServiceImpl service;

    @Autowired
    private CodeServerProperties config;

    @Before
    public void setUp() {
        //clean up mock object for each test
        reset(repositoryService);
        reset(revisionService);
    }

    @Test
    public void testClone() throws Exception {
        Repository repository = EntityFactory.newLongMethodScmRepo();

        service.clone(repository, true);

        assertEquals(RepositoryCheckoutStatus.IMPORTED, repository.getStatus().getCheckoutStatus());

        verify(repositoryService, times(2)).update(repository);
        verify(revisionService, times(1)).save(any(), any(Revision.class));
    }

    @Test
    public void testCloneError() throws Exception {
        Repository repository = EntityFactory.newFailScmRepo();

        service.clone(repository, true);

        assertEquals(RepositoryCheckoutStatus.NOT_INITIALIZED, repository.getStatus().getCheckoutStatus());
        assertEquals("Error while cloning git repository: https://asdasdasd.com/aggfbw/anyproject.git: cannot open git-upload-pack", repository.getStatus().getError());
    }

    @Test
    public void testPull() throws Exception {
        Repository repository = EntityFactory.newLongMethodScmRepo();

        service.clone(repository, true);

        reset(repositoryService); //clean clone effects
        reset(revisionService);

        service.pull(repository, true);

        assertEquals(RepositoryCheckoutStatus.IMPORTED, repository.getStatus().getCheckoutStatus());

        verify(repositoryService, times(1)).update(repository);//no pull is needed
    }

    @Test
    public void dontChangeRepositoryReadyStatusWhenNothingToPull() throws Exception {
        Repository repository = EntityFactory.newLongMethodScmRepo();

        service.clone(repository, true);

        reset(repositoryService); //clean clone effects

        repository.makeReady();

        service.pull(repository, true);

        assertEquals(RepositoryCheckoutStatus.READY, repository.getStatus().getCheckoutStatus());

        verify(repositoryService, times(1)).update(repository);
    }

    @Test
    public void testPullError() throws Exception {
        Repository repository = EntityFactory.newLongMethodScmRepo();
        repository.makeImported();

        service.pull(repository, true);

        assertEquals(RepositoryCheckoutStatus.IMPORTING_ERROR, repository.getStatus().getCheckoutStatus());
        assertThat(repository.getStatus().getError()).contains("Error while pulling git repository: repository not found: ");
    }

    @Test
    public void testCloneAuthenticationMissing() throws Exception {
        List<ScmCredential> previous = config.getScmConfigs();
        config.setScmConfigs(Collections.emptyList());

        Repository repository = EntityFactory.newSourceAPIScmRepo();

        service.clone(repository, true);
        assertEquals(RepositoryCheckoutStatus.NOT_INITIALIZED, repository.getStatus().getCheckoutStatus());
        assertTrue(repository.getStatus().getError().endsWith("Authentication is required but no CredentialsProvider has been registered"));

        config.setScmConfigs(previous);
    }

    @Test
    public void testCloneAuthenticationError() throws Exception {
        ScmCredential cred = new ScmCredential();
        cred.setHost(new URL("https://scm.devfactory.com"));
        cred.setUserName("anyuser");
        cred.setPassword("anypasssssssssssss");

        List<ScmCredential> previous = config.getScmConfigs();
        config.setScmConfigs(Collections.singletonList(cred));

        Repository repository = EntityFactory.newSourceAPIScmRepo();

        service.clone(repository, true);
        assertEquals(RepositoryCheckoutStatus.NOT_INITIALIZED, repository.getStatus().getCheckoutStatus());
        assertTrue(repository.getStatus().getError().endsWith("not authorized"));

        config.setScmConfigs(previous);
    }

}
