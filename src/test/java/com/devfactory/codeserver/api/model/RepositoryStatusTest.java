package com.devfactory.codeserver.api.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import com.devfactory.codeserver.api.ModelTest;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class to test entity.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public class RepositoryStatusTest extends ModelTest<RepositoryStatus> {

    public RepositoryStatusTest() {
        super(new TypeReference<RepositoryStatus>() {});
    }

    @Override
    public RepositoryStatus getInstance() {
        RepositoryStatus status = new RepositoryStatus();
        status.setError("any");
        status.setCheckoutStatus(RepositoryCheckoutStatus.IMPORTING);
        status.setLastUpdateCommits(241);
        status.setLastUpdated(LocalDateTime.of(2016, 10, 25, 0, 0, 1));
        status.setLastVerified(LocalDateTime.of(2016, 10, 25, 0, 0, 2));
        return status;
    }

    @Override
    public void testProperties() {
        assertEquals("any", instance.getError());
        assertEquals(RepositoryCheckoutStatus.IMPORTING, instance.getCheckoutStatus());
        assertEquals(241, instance.getLastUpdateCommits());
        assertEquals(LocalDateTime.of(2016, 10, 25, 0, 0, 1), instance.getLastUpdated());
        assertEquals(LocalDateTime.of(2016, 10, 25, 0, 0, 2), instance.getLastVerified());
    }

}
