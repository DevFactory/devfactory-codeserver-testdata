package com.devfactory.codeserver.api.model;

import lombok.Data;

/**
 * Class to represent Git repository.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@Data
public class SourceApiDetails {

    private Status status;

    private String error;

    public static enum Status {

        NOT_INITIALIZED,
        IMPORTING,
        IMPORTING_ERROR,
        READY;

    }

}
