package edu.t1.chernykh.controller;

import edu.t1.chernykh.dto.AccountDto;
import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.entity.Transaction;
import edu.t1.chernykh.repository.TransactionRepository;
import edu.t1.chernykh.service.AccountService;
import edu.t1.chernykh.util.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountUnlockController {
    private final AccountMapper accountMapper;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountUnlockController(AccountMapper accountMapper, AccountService accountService, TransactionRepository transactionRepository) {
        this.accountMapper = accountMapper;
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }

    @PatchMapping("/account/unlock")
    public ResponseEntity<String> unlockAccount(AccountDto accountDto, @RequestParam(name = "transactionId") Long transactionId) {
        Account account = accountMapper.toAccount(accountDto);
        Transaction transaction;
        switch (account.getType()) {
            case CREDIT -> {
                transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));
                accountService.unlockCreditAccount(account, transaction);
            } case DEPOSIT -> {
                accountService.unlockDebitAccount(account);
            }
        }
        return ResponseEntity.ok("Account successfully unlocked");
    }
}
