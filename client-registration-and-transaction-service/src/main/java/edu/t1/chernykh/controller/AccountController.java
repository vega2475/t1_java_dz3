package edu.t1.chernykh.controller;

import edu.t1.chernykh.dto.AccountDto;
import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.repository.AccountRepository;
import edu.t1.chernykh.util.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register")
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountController(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @PostMapping
    public ResponseEntity<String> register(AccountDto accountDto){
        Account account = accountMapper.toAccount(accountDto);
        accountRepository.save(account);

        return ResponseEntity.ok("Accounted successfully created");
    }
}
