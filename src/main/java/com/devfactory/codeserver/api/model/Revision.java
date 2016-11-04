package com.devfactory.codeserver.api.model;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import com.devfactory.codeserver.api.CodeServerProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of = {"repositoryId", "scmRev"})
@NoArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"inProgress"})
public class Revision {
    private String id = UUID.randomUUID().toString();

    @NonNull
    private Integer repositoryId;

    @NonNull
    private String scmRev;

    private Date date;

    private String author;

    private String message;

    //Current date format expects zone to be part of serialized string
    //LocalDateTime doesn't have tomezone info. Hence using ZonedDateTime
    @NonNull
    @JsonFormat(pattern = CodeServerProperties.DATE_FORMAT)
    private ZonedDateTime commitTime;

    //Current date format expects zone to be part of serialized string
    //LocalDateTime doesn't have tomezone info. Hence using ZonedDateTime
    @JsonFormat(pattern = CodeServerProperties.DATE_FORMAT)
    private ZonedDateTime lastUpdated;

    private RevisionStatus status = RevisionStatus.PENDING;

    private String error;

    public boolean canRun() {
        return this.status == RevisionStatus.PENDING || this.status == RevisionStatus.ANALYZING_ERROR;
    }

    public void analyzing() {
        updateStatus(RevisionStatus.ANALYZING, null);
    }

    private void updateStatus(RevisionStatus status, String error) {
        this.status = status;
        this.error  = error;
        this.lastUpdated = ZonedDateTime.now();
    }

    public void analyzed() {
        updateStatus(RevisionStatus.ANALYZED, null);
    }

    public void failed(String error) {
        updateStatus(RevisionStatus.ANALYZING_ERROR, error);
    }

    public boolean isInProgress() {
        return this.status == RevisionStatus.ANALYZING;
    }
}
