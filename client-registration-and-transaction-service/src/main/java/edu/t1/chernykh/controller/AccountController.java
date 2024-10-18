package edu.t1.chernykh.controller;

import edu.t1.chernykh.dto.AccountDto;
import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.entity.AccountType;
import edu.t1.chernykh.repository.AccountRepository;
import edu.t1.chernykh.util.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountController(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AccountDto accountDto){
        Account account = accountMapper.toAccount(accountDto);
        accountRepository.save(account);

        return ResponseEntity.ok("Accounted successfully created");
    }

    @PatchMapping("/block-deposit-account/{id}")
    public ResponseEntity<String> blockDepositAccount(@PathVariable(name = "id") Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        if(account.getType() != AccountType.DEPOSIT){
            return ResponseEntity.badRequest().body("account not debit!");
        }
        account.setBlocked(true);
        accountRepository.save(account);
        return ResponseEntity.ok("Deposit account was blocked");
    }
}
