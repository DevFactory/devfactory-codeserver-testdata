package com.devfactory.codeserver.api.dto;

import com.devfactory.codeserver.api.CodeServerProperties;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * Class to represent repository's DTOs.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public abstract class RevisionDTO {

    @Data
    public static class RevisionDTOInputSave implements DTO {

        @NotNull
        @Size(min = 1)
        private String scmRev;

        @NotNull
        @JsonFormat(pattern = CodeServerProperties.DATE_FORMAT)
        private Date date; 

        @NotNull
        @Size(min = 1)
        private String author;

        @NotNull
        @Size(min = 1)
        private String message;

    }

    @Data
    public static class RevisionDTOOutput implements DTO {

        private String id;

        private String scmRev;

        @JsonFormat(pattern = CodeServerProperties.DATE_FORMAT)
        private Date date;

        @JsonFormat(pattern = CodeServerProperties.DATE_FORMAT)
        private ZonedDateTime commitTime;

        private String author;

        private String message;
    }
}
