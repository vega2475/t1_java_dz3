package edu.t1.chernykh.util;

import edu.t1.chernykh.dto.AccountDto;
import edu.t1.chernykh.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toAccount(AccountDto accountDto){
        return new Account(
                accountDto.clientId(), accountDto.type(),
                accountDto.balance(), accountDto.isBlocked()
        );
    }
}
