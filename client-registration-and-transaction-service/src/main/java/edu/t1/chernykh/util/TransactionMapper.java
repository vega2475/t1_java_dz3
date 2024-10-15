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
    // Что бы не подтягивать отправителя и получателя по id из БД и не нагружать БД можно было бы передавать их в JSON теле
    public Transaction toTransaction(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        transaction.setAmount(transaction.getAmount());
        Account sender = accountRepository.findById(transactionDto.senderId()).orElseThrow();
        Account receiver = accountRepository.findById(transactionDto.receiverId()).orElseThrow();
        transaction.setSenderAccount(sender);
        transaction.setReceiverAccount(receiver);
        return transaction;
    }

    public TransactionDto transactionDto(Transaction transaction){
        return new TransactionDto(transaction.getAmount(),
                transaction.getSenderAccount().getId(),
                transaction.getReceiverAccount().getId());
    }
}
