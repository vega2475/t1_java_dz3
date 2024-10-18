package edu.t1.chernykh.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.t1.chernykh.entity.TransactionType;

import java.io.Serializable;

/**
 * DTO for {@link edu.t1.chernykh.entity.Transaction}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionDto(Long id, Double amount, Long accountId, TransactionType type) implements Serializable {
}