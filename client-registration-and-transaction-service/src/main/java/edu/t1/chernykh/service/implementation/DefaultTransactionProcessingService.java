package edu.t1.chernykh.service.implementation;

import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.entity.AccountType;
import edu.t1.chernykh.entity.Transaction;
import edu.t1.chernykh.entity.TransactionType;
import edu.t1.chernykh.repository.AccountRepository;
import edu.t1.chernykh.repository.TransactionRepository;
import edu.t1.chernykh.service.TransactionProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultTransactionProcessingService implements TransactionProcessingService {
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, Long> kafkaTemplate;
    private final AccountRepository accountRepository;

    @Value("${t1.kafka.topic.transaction_errors_topic}")
    private String transactionErrorTopic;

    @Autowired
    public DefaultTransactionProcessingService(TransactionRepository transactionRepository, KafkaTemplate<String, Long> kafkaTemplate, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean doApprovalTransactionProcess(Transaction transaction) {
        Account account = transaction.getAccount();

        if(account.getBlocked()){
             processWithBlockedAccount(transaction.getId());
             return false;
        } else {
            return processTransaction(account, transaction);
        }
    }

    private boolean processTransaction(Account account, Transaction transaction) {
        if(account.getType() == AccountType.CREDIT){
            switch (transaction.getType()){
                case DEBIT -> {
                    if(account.getBalance() < transaction.getAmount()){
                        account.setBlocked(true);
                        accountRepository.save(account);
                        processWithBlockedAccount(transaction.getId());
                        return false;
                    } else {
                        processDebit(account, transaction);
                        return true;
                    }
                } case ACCRUAL -> {
                    processAccrual(account, transaction);
                    return true;
                } default -> throw new UnsupportedOperationException("Unsupported transaction type");
            }
        } else if (account.getType() == AccountType.DEPOSIT){
            switch (transaction.getType()){
                case ACCRUAL -> {
                    processAccrual(account, transaction);
                    return true;
                } case DEBIT -> {
                    if (account.getBalance() < transaction.getAmount()){
                        return false;
                    } else {
                        processDebit(account, transaction);
                        return true;
                    }
                }default -> throw new UnsupportedOperationException("Unsupported transaction type");
            }
        }
        throw new UnsupportedOperationException("Unhandled account type");
    }

    private void processWithBlockedAccount(Long transactionId) {
        kafkaTemplate.send(transactionErrorTopic, transactionId);
    }

    private void processAccrual(Account account, Transaction transaction){
        account.setBalance(account.getBalance() + transaction.getAmount());
        accountRepository.save(account);
        transactionRepository.save(transaction);
    }

    private void processDebit(Account account, Transaction transaction){
        account.setBalance(account.getBalance() - transaction.getAmount());
        accountRepository.save(account);
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    // Можно было бы отсылать TransactionDto, что бы не делать доп запросы к БД
    public void doCancelationTransactionalProcess(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Transaction doesnt exist in Database"));

        Account account = transaction.getAccount();
        Double amount = transaction.getAmount();

        if(transaction.getType() == TransactionType.DEBIT){
            account.setBalance(account.getBalance() + amount);
            accountRepository.save(account);
        } else if (transaction.getType() == TransactionType.ACCRUAL){
            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);
        }
        transactionRepository.delete(transaction);
    }
}