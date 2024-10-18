package edu.t1.chernykh.kafka;

import edu.t1.chernykh.entity.Transaction;
import edu.t1.chernykh.service.AccountUnlockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class TransactionErrorKafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(TransactionErrorKafkaConsumer.class);
    private final AccountUnlockService accountUnlockService;

    @Autowired
    public TransactionErrorKafkaConsumer(AccountUnlockService accountUnlockService) {
        this.accountUnlockService = accountUnlockService;
    }

    @KafkaListener(id = "${t1.kafka.consumer.group-id}",
            topics = "${t1.kafka.topic.transaction_errors_topic}",
            containerFactory = "transactionDtoConcurrentKafkaListenerContainerFactory"
    )
    public void listenTransactions(@Payload Long transactionId,
                                   Acknowledgment acknowledgment) {
        log.info("Начало обработки ошибки транзакции с id {}", transactionId);
        try {
            accountUnlockService.processMessage(transactionId);
        } finally {
            acknowledgment.acknowledge();
        }
        log.info("Транзакция с id {} обработана", transactionId);
    }
}
