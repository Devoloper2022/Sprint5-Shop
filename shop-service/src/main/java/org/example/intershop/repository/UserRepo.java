package org.example.intershop.repository;


import org.example.intershop.models.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepo extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<UserEntity> findByUsername(String username);
}
