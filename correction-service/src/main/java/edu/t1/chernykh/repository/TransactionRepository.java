package edu.t1.chernykh.repository;

import edu.t1.chernykh.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Set<Transaction> findAllByIsError(Boolean isError);
}
