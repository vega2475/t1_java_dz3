package edu.t1.chernykh.service.implementation;

import edu.t1.chernykh.client.AccountUnlockClient;
import edu.t1.chernykh.dto.AccountDto;
import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.repository.AccountRepository;
import edu.t1.chernykh.repository.TransactionRepository;
import edu.t1.chernykh.service.AccountUnlockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultAccountUnlockService implements AccountUnlockService {

    private static final Logger log = LoggerFactory.getLogger(DefaultAccountUnlockService.class);
    private final AccountUnlockClient accountUnlockClient;
    private final TransactionRepository transactionRepository;

    public DefaultAccountUnlockService(AccountUnlockClient accountUnlockClient, TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.accountUnlockClient = accountUnlockClient;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public void processMessage(Long transactionId) {
        String response = accountUnlockClient.sendUnlockRequest(transactionId);
        if (response.equals("Account successfully unlocked")) {
            transactionRepository.deleteById(transactionId);
        } else {
            // Транзакция уже создана в БД
            log.warn("Account is not unblocked");
        }
    }
}
