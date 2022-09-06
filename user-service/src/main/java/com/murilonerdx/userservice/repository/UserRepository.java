package com.murilonerdx.userservice.repository;

import com.murilonerdx.userservice.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    @Modifying
    @Query(
            "update users " +
                    "set balance = balance - :amount " +
                    "where id = :userId " +
                    "and balance >= :amount"
    )
    Mono<Boolean> updateUserBalance(int userId, int amount);
}
