package com.devfactory.codeserver.api.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(includeFieldNames = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, 500, "Internal Server Error"),
    METADATA_CREATION_FAILED(1, 502, "Failed to Create Metadata"),
    OPEN_CONNECTION_FAILED(2, 502, "Failed to Open Metadata Connection"),
    COMMAND_EXECUTION_FAILED(3, 502, "Failed Execute Command"),
    GIT_PROBLEM(4, 502, "Problem With Git Details Provided"),
    INVALID_ARGUMENTS(5, 400, "Invalid arguments were provided."),
    DOWNLOADABLE_NOT_AVAILABLE(6, 404, "Downloadable not available."),
    PROCESSING_PREVIOUS_STEPS(7, 400, "Complete previous steps before starting processing."),
    PROCESSING_NOT_YET_STARTED(8, 404, "Processing not yet started."),
    PROCESSING_ALREADY_CALLED(9, 400, "Processing already called."),
    PROCESSING_ERROR(10, 502, "Processing failled."),
    PROCESSING_NOT_YET_DONE(11, 404, "Processing not yet done."),
    GIT_NOT_YET_STARTED(12, 404, "Git repository cloning not yet started."),
    GIT_ALREADY_CALLED(13, 400, "Git repository cloning have already been called."),
    GIT_ERROR(14, 500, "Git repository cloning failed."),
    GIT_NOT_YET_DONE(15, 404, "Git repository cloning not yet finished."),
    REPOSITORY_DOES_NOT_EXIST(16, 400, "Repository does not exist."),
    UNKNOWN_SCM_REVISION(17, 404, "Unknown svm revision.");

    private final int code;
    
    private final int httpCode;
    
    private final String message;

}
