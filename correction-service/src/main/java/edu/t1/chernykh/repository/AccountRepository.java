package edu.t1.chernykh.repository;

import edu.t1.chernykh.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
