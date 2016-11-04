package com.devfactory.codeserver.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Interface to represent DTOs.
 *
 * @author Wander Costa (wander.costa@aurea.com)
 * @version 1.0
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface DTO {

}
