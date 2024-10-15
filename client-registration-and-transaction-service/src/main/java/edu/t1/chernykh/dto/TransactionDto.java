package edu.t1.chernykh.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link edu.t1.chernykh.entity.Transaction}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionDto(Double amount, Long senderId, Long receiverId) implements Serializable {
    @Override
    public String toString() {
        return "TransactionDto{" +
                "amount=" + amount +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                '}';
    }
}