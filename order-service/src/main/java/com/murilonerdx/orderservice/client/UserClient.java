package com.murilonerdx.orderservice.client;

import com.murilonerdx.orderservice.dto.TransactionRequestDTO;
import com.murilonerdx.orderservice.dto.TransactionResponseDTO;
import com.murilonerdx.orderservice.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {

    private final WebClient webClient;

    public UserClient(@Value("${user.service.url}") String url){
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<TransactionResponseDTO> authorizeTransaction(TransactionRequestDTO requestDTO){
        return this.webClient
                .post()
                .uri("transaction")
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(TransactionResponseDTO.class);
    }

    public Flux<UserDTO> getAllUsers(){
        return this.webClient
                .get()
                .uri("all")
                .retrieve()
                .bodyToFlux(UserDTO.class);
    }
}
