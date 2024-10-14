package edu.t1.chernykh.service.implementation;

import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.repository.AccountRepository;
import edu.t1.chernykh.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultAccountService implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public DefaultAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }
}
