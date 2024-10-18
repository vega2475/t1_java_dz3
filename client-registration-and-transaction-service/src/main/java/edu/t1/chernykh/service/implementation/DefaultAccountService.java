package edu.t1.chernykh.service.implementation;

import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.entity.Transaction;
import edu.t1.chernykh.exception.AccountUnlockException;
import edu.t1.chernykh.repository.AccountRepository;
import edu.t1.chernykh.service.AccountService;
import edu.t1.chernykh.service.TransactionProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultAccountService implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(DefaultAccountService.class);
    private final AccountRepository accountRepository;
    private final TransactionProcessingService transactionProcessingService;

    @Autowired
    public DefaultAccountService(AccountRepository accountRepository, TransactionProcessingService transactionProcessingService) {
        this.accountRepository = accountRepository;
        this.transactionProcessingService = transactionProcessingService;
    }

    @Override
    public void unlockCreditAccount(Account account, Transaction transaction) {
        if (account.getBalance() >= transaction.getAmount()) {
            account.setBlocked(false);
            accountRepository.save(account);
            transactionProcessingService.doApprovalTransactionProcess(transaction);
        } else {
            log.warn("Account didnt unblock");
            throw new AccountUnlockException("Account didnt unlock");
        }
    }

    @Override
    public void unlockDebitAccount(Account account) {
        account.setBlocked(false);
        accountRepository.save(account);
    }
}