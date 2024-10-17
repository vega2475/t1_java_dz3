package edu.t1.chernykh.service;

import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.entity.Transaction;
import org.springframework.stereotype.Service;

public interface AccountService {
    void unlockCreditAccount(Account account, Transaction transaction);
    void unlockDebitAccount(Account account);
}
