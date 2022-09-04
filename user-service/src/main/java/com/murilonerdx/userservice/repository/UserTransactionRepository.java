package com.murilonerdx.userservice.repository;

import com.murilonerdx.userservice.entity.User;
import com.murilonerdx.userservice.entity.UserTransaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.jta.UserTransactionAdapter;
import reactor.core.publisher.Flux;

@Repository
public interface UserTransactionRepository extends ReactiveCrudRepository<UserTransaction, Integer> {
    Flux<UserTransaction> findByUserId(int userId);
}
