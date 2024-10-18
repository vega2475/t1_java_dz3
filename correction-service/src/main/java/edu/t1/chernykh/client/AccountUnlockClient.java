package edu.t1.chernykh.client;

import edu.t1.chernykh.dto.AccountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccountUnlockClient {
    private static final Logger log = LoggerFactory.getLogger(AccountUnlockClient.class);
    private final WebClient webClient;

    @Autowired
    public AccountUnlockClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String sendUnlockRequest(Long transactionId) {
        String response = webClient.patch().uri(uriBuilder -> uriBuilder
                .path("api/v1/account/unlock")
                .queryParam("transactionId", transactionId)
                .build())
                .retrieve().bodyToMono(String.class).block();

        log.info(response);
        return response;
    }
}
