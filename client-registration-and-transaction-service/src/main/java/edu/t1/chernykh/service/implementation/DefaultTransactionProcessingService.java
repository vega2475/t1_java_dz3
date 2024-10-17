package edu.t1.chernykh.service.implementation;

import edu.t1.chernykh.dto.TransactionDto;
import edu.t1.chernykh.entity.Account;
import edu.t1.chernykh.entity.AccountType;
import edu.t1.chernykh.entity.Transaction;
import edu.t1.chernykh.repository.AccountRepository;
import edu.t1.chernykh.repository.TransactionRepository;
import edu.t1.chernykh.service.TransactionProcessingService;
import edu.t1.chernykh.util.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultTransactionProcessingService implements TransactionProcessingService {
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, TransactionDto> kafkaTemplate;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    @Value("${t1.kafka.topic.transaction_errors_topic}")
    private String transactionErrorTopic;

    @Autowired
    public DefaultTransactionProcessingService(TransactionRepository transactionRepository, KafkaTemplate<String, TransactionDto> kafkaTemplate, TransactionMapper transactionMapper, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.transactionMapper = transactionMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean doApprovalTransactionProcess(Transaction transaction) {
        Account sender = transaction.getSenderAccount();
        Account receiver = transaction.getReceiverAccount();
        Double amount = transaction.getAmount();

        if(sender.getBlocked() || receiver.getBlocked()){
            kafkaTemplate.send(transactionErrorTopic, transactionMapper.toTransactionDto(transaction));
            return false;
        } else {
            // Кредитный лимит исчерпан - блокируем счет
            if(sender.getType() == AccountType.CREDIT &&
                    sender.getBalance() < transaction.getAmount()){
                sender.setBlocked(true);
                accountRepository.save(sender);
                kafkaTemplate.send(transactionErrorTopic, transactionMapper.toTransactionDto(transaction));
                return false;
            }
            if(sender.getBalance() < amount){
                return false;
            }
            sender.setBalance(sender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);
            transactionRepository.save(transaction);
            return true;
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    // Можно было бы отсылать TransactionDto, что бы не делать доп запросы к БД
    public void doCancelationTransactionalProcess(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Transaction doesnt exist in Database"));

        Account sender = transaction.getSenderAccount();
        Account receiver = transaction.getReceiverAccount();
        Double amount = transaction.getAmount();

        sender.setBalance(sender.getBalance() + amount);
        receiver.setBalance(receiver.getBalance() - amount);
        transactionRepository.delete(transaction);
    }
}
