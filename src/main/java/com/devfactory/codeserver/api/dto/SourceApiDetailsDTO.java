package com.devfactory.codeserver.api.dto;

import com.devfactory.codeserver.api.model.SourceApiDetails.Status;
import lombok.Data;

/**
 * Class to represent DTO of SourceApi interaction.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
public abstract class SourceApiDetailsDTO {

    @Data
    public static class SourceApiDetailsDTOOutput implements DTO {

        private Status status;

        private String error;

    }

}
