package edu.t1.chernykh.kafka;

import edu.t1.chernykh.dto.TransactionDto;
import edu.t1.chernykh.service.TransactionProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaTransactionConsumer {
    private final TransactionProcessingService transactionProcessingService;

    @Autowired
    public KafkaTransactionConsumer(TransactionProcessingService transactionProcessingService) {
        this.transactionProcessingService = transactionProcessingService;
    }

    @Value("${t1.kafka.consumer.group-id}")
    private String groupId;

    @Value("${t1.kafka.topic.transaction_topic}")
    private String transactionTopic;

    @KafkaListener(id = "${t1.kafka.consumer.group-id}",
            topics = "${t1.kafka.topic.transaction_topic}",
            containerFactory = "transactionDtoConcurrentKafkaListenerContainerFactory"
    )
    public void listener(@Payload List<TransactionDto> transactionDtoList){

    }
}
