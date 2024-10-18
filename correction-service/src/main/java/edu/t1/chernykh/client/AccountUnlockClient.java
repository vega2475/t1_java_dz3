package edu.t1.chernykh.client;

import edu.t1.chernykh.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AccountUnlockClient {
    private final WebClient webClient;

    @Autowired
    public AccountUnlockClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String sendUnlockRequest(AccountDto accountDto, Long transactionId) {
        return webClient.patch().uri(uriBuilder -> uriBuilder
                .path("api/v1/account/unlock")
                .queryParam("transactionId", transactionId)
                .build()).body(BodyInserters.fromValue(accountDto))
                .retrieve().bodyToMono(String.class).block();
    }
}
