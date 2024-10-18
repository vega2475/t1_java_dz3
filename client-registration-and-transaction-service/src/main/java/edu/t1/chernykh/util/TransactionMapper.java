package edu.t1.chernykh.util;

import edu.t1.chernykh.dto.TransactionDto;
import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.entity.Transaction;
import edu.t1.chernykh.repository.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private final AccountRepository accountRepository;

    public TransactionMapper(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    // Что бы не подтягивать счет по id из БД и не нагружать БД можно было бы передавать его в JSON теле
    public Transaction toTransaction(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.amount());
        Account account = accountRepository.findById(transactionDto.accountId()).orElseThrow();
        transaction.setId(transactionDto.id());
        transaction.setAccount(account);
        transaction.setType(transactionDto.type());
        return transaction;
    }

    public TransactionDto toTransactionDto(Transaction transaction){
        return new TransactionDto(transaction.getId(), transaction.getAmount(),
                transaction.getAccount().getId(),
                transaction.getType());
    }
}
