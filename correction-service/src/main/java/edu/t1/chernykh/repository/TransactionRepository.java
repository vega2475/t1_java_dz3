package edu.t1.chernykh.repository;

import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Account, Long> {
}
