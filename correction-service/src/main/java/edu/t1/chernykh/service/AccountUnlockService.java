package edu.t1.chernykh.service;

import edu.t1.chernykh.dto.AccountDto;

public interface AccountUnlockService {
    void processMessage(AccountDto accountDto, Long transactionId);
}
