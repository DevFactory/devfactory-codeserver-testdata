package com.devfactory.codeserver.api.dto;

import java.util.Date;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.devfactory.codeserver.api.CodeServerProperties;
import com.devfactory.codeserver.api.model.RepositoryStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Class to represent repository's DTOs.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public abstract class RepositoryDTO {

    @Data
    public static class RepositoryDTOInputSave implements DTO {

        @NotNull
        @Length(min = 1, max = 100)
        private String name;

        @NotNull
        @NotEmpty
        private String scmUrl;

        @Min(1)
        private Integer parentId;

        private Boolean create;

        private Date validDate;

    }

    @Data
    public static class RepositoryDTOInputUpdate implements DTO {

        @Length(min = 1, max = 100)
        private String name;

        @Min(1)
        private Integer parentId;

        private Date endDate;

    }

    @Data
    public static class RepositoryDTOOutput implements DTO {

        private Integer id;

        private String name;

        private String scmUrl;

        private String shortUrl;

        private String branch;

        private RevisionDTO.RevisionDTOOutput headRevision;

        private Integer parentId;

        private Boolean create;

        private Map<String, Double> language;

        @JsonFormat(pattern = CodeServerProperties.DATE_FORMAT)
        private Date startDate; 

        @JsonFormat(pattern = CodeServerProperties.DATE_FORMAT)
        private Date endDate;

        private RepositoryStatus status;

    }

}
