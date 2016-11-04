package com.devfactory.codeserver.api.service;

import com.devfactory.codeserver.api.BaseIntegrationTest;
import com.devfactory.codeserver.api.EntityFactory;
import com.devfactory.codeserver.api.model.Repository;
import com.devfactory.codeserver.api.model.RepositoryCheckoutStatus;
import com.devfactory.codeserver.api.service.impl.ScmInteractionServiceImpl;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Class to test service implementation.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScmInteractionServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ScmInteractionServiceImpl service;

    @Test
    public void test_100_Clone() throws Exception {
        repositoryService.deleteAll();
        Repository repository = EntityFactory.newLongMethodScmRepo();
        Integer id = repositoryService.save(repository);

        service.clone(repository, true);
        assertEquals(RepositoryCheckoutStatus.IMPORTED, repositoryService.find(id).getStatus().getCheckoutStatus());
    }

    @Test
    public void test_200_Pull() throws Exception {
        Repository repository = repositoryService.findAll().get(0);

        service.pull(repository, true);
        assertEquals(RepositoryCheckoutStatus.IMPORTED, repositoryService.find(repository.getId()).getStatus().getCheckoutStatus());
    }

}
