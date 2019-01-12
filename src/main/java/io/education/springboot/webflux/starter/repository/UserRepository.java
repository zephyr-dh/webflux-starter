package io.education.springboot.webflux.starter.repository;

import io.education.springboot.webflux.starter.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @auther zephyr
 * @create 2019-01-10 12:40 PM
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User,String> {
    Mono<User> findByUsername(String username);
    Mono<Long> deleteByUsername(String username);
}
