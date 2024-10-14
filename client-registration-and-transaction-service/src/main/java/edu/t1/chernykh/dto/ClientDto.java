package edu.t1.chernykh.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO for {@link edu.t1.chernykh.entity.Client}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ClientDto(String firstName, String lastName, String middleName) implements Serializable {
}