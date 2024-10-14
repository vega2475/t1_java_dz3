package edu.t1.chernykh.service.implementation;

import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.entity.Transaction;
import edu.t1.chernykh.repository.TransactionRepository;
import edu.t1.chernykh.service.TransactionProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultTransactionProcessingService implements TransactionProcessingService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public DefaultTransactionProcessingService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean doApprovalTransactionProcess(Transaction transaction) {
        Account sender = transaction.getSenderAccount();
        Account receiver = transaction.getReceiverAccount();
        Double amount = transaction.getAmount();

        if(sender.getBlocked() || receiver.getBlocked()){
            //TODO отправить ошибочную транзакцию в топик
            return false;
        } else {
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            transactionRepository.save(transaction);
            return true;
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void doCancelationTransactionalProcess(Transaction transaction) {
        Account sender = transaction.getSenderAccount();
        Account receiver = transaction.getReceiverAccount();
        Double amount = transaction.getAmount();

        sender.setBalance(sender.getBalance() + amount);
        receiver.setBalance(receiver.getBalance() - amount);
        transactionRepository.delete(transaction);
    }
}
