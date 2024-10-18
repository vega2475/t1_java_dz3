package edu.t1.chernykh.controller;

import edu.t1.chernykh.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionSenderController {
    @Value("${t1.kafka.topic.transaction_topic}")
    private String topic;

    private final KafkaTemplate<String, TransactionDto> kafkaTemplate;

    @Autowired
    public TransactionSenderController(KafkaTemplate<String, TransactionDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/send")
    public String sendTransaction(TransactionDto transactionDto){
        kafkaTemplate.send(topic, transactionDto);
        return "Transaction has sent";
    }
}
