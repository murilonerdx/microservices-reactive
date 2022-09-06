package com.murilonerdx.userservice.controller;

import com.murilonerdx.userservice.dto.TransactionRequestDTO;
import com.murilonerdx.userservice.dto.TransactionResponseDTO;
import com.murilonerdx.userservice.entity.UserTransaction;
import com.murilonerdx.userservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDTO> createTransaction(@RequestBody Mono<TransactionRequestDTO> requestDtoMono){
        return requestDtoMono.flatMap(this.transactionService::createTransaction);
    }

    @GetMapping
    public Flux<UserTransaction> getByUserId(@RequestParam("userId") int userId){
        return this.transactionService.getByUserId(userId);
    }

}
