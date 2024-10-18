package edu.t1.chernykh.service.implementation;

import edu.t1.chernykh.dto.TransactionDto;
import edu.t1.chernykh.entity.Transaction;
import edu.t1.chernykh.repository.TransactionRepository;
import edu.t1.chernykh.service.ErrorTransactionSendProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DefaultErrorTransactionSendProcessor implements ErrorTransactionSendProcessor {
    private static final Logger log = LoggerFactory.getLogger(DefaultErrorTransactionSendProcessor.class);
    @Value("${t1.kafka.topic.transaction_topic}")
    private String transactionTopic;

    private final KafkaTemplate<String, TransactionDto> kafkaTemplate;
    private final TransactionRepository transactionRepository;

    @Autowired
    public DefaultErrorTransactionSendProcessor(KafkaTemplate<String, TransactionDto> kafkaTemplate, TransactionRepository transactionRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Scheduled(fixedDelayString = "${t1.kafka.resend-interval}")
    public void errorTransactionSendProcessing() {
        Set<Transaction> transactionList = transactionRepository.findAllByIsError(true);
        if(transactionList.isEmpty()){
            log.info("Error transaction doesnt exist in database");
            return;
        }

        transactionList.forEach(
                transaction -> {
                    TransactionDto transactionDto = new TransactionDto(transaction.getId(), transaction.getAmount(), transaction.getAccount().getId(), transaction.getType());
                    kafkaTemplate.send(transactionTopic, transactionDto);
                }
        );

        log.info("Error transaction sent on process");
    }
}
