package com.devfactory.codeserver.api.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Class to represent Git repository.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class RepositoryStatus {

    private LocalDateTime lastVerified;

    private LocalDateTime lastUpdated;

    private int lastUpdateCommits;

    @NonNull
    private RepositoryCheckoutStatus checkoutStatus;

    private String error;

    public static RepositoryStatus notInitialized() {
        return new RepositoryStatus(RepositoryCheckoutStatus.NOT_INITIALIZED);
    }
}
