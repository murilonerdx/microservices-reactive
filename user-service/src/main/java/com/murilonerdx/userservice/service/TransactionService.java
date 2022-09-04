package com.murilonerdx.userservice.service;

import com.murilonerdx.userservice.dto.TransactionRequestDTO;
import com.murilonerdx.userservice.dto.TransactionResponseDTO;
import com.murilonerdx.userservice.dto.TransactionStatus;
import com.murilonerdx.userservice.entity.UserTransaction;
import com.murilonerdx.userservice.repository.UserRepository;
import com.murilonerdx.userservice.repository.UserTransactionRepository;
import com.murilonerdx.userservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTransactionRepository transactionRepository;

    public Mono<TransactionResponseDTO> createTransaction(final TransactionRequestDTO requestDto){
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDTOUtil.toEntity(requestDto))
                .flatMap(this.transactionRepository::save)
                .map(ut -> EntityDTOUtil.toDTO(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDTOUtil.toDTO(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(int userId){
        return this.transactionRepository.findByUserId(userId);
    }

}