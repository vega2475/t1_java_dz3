package edu.t1.chernykh.kafka;

import edu.t1.chernykh.dto.TransactionDto;
import edu.t1.chernykh.entity.Transaction;
import edu.t1.chernykh.service.TransactionProcessingService;
import edu.t1.chernykh.util.TransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaTransactionConsumer {
    private static final Logger log = LoggerFactory.getLogger(KafkaTransactionConsumer.class);

    private final TransactionProcessingService transactionProcessingService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public KafkaTransactionConsumer(TransactionProcessingService transactionProcessingService, TransactionMapper transactionMapper) {
        this.transactionProcessingService = transactionProcessingService;
        this.transactionMapper = transactionMapper;
    }

    @KafkaListener(id = "${t1.kafka.consumer.group-id}",
            topics = "${t1.kafka.topic.transaction_topic}",
            containerFactory = "transactionDtoConcurrentKafkaListenerContainerFactory"
    )
    public void listenTransactions(@Payload List<TransactionDto> transactionDtoList,
                                   Acknowledgment acknowledgment) {
        log.info("Начало обработки сообщений {}", transactionDtoList);
        try {
            transactionDtoList.forEach(transactionDto -> {
                Transaction transaction = transactionMapper.toTransaction(transactionDto);
                transaction.setError(false);
                transactionProcessingService.doApprovalTransactionProcess(transaction);
            });
        } finally {
            acknowledgment.acknowledge();
        }
        log.info("Сообщения {} обработаны", transactionDtoList);
    }
}