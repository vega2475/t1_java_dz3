package edu.t1.chernykh.config;

import edu.t1.chernykh.dto.TransactionDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Value("${t1.kafka.bootstrap.server}")
    private String servers;

    @Bean
    public ProducerFactory<String, TransactionDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, 10);
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "edu.t1.chernykh.dto.TransactionDto");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    @ConditionalOnProperty(name = "t1.kafka.producer.enable", havingValue = "true", matchIfMissing = true)
    public KafkaTemplate<String, TransactionDto> transactionProducerKafkaTemplate(ProducerFactory<String, TransactionDto> producerFactory){
        return new KafkaTemplate<>(producerFactory);
    }
}
