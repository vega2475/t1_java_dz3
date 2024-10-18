package edu.t1.chernykh.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.t1.chernykh.entity.AccountType;

import java.io.Serializable;

/**
 * DTO for {@link edu.t1.chernykh.entity.Account}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AccountDto(Long clientId, AccountType type, Double balance, Boolean isBlocked) implements Serializable {
}