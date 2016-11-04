package com.devfactory.codeserver.api.model;

/**
 * Enumeration to represent repositories statuses.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public enum RepositoryCheckoutStatus {

    NOT_INITIALIZED,
    IMPORTING,
    IMPORTING_ERROR,
    IMPORTED,
    READY;
}
