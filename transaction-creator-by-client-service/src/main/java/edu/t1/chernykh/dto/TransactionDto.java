package edu.t1.chernykh.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionDto(Double amount, Long accountId, TransactionType type) implements Serializable {
}
