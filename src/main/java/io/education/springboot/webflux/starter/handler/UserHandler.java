package io.education.springboot.webflux.starter.handler;

import io.education.springboot.webflux.starter.domain.User;
import io.education.springboot.webflux.starter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @Description
 * @auther zephyr
 * @create 2019-01-10 12:45 PM
 */

@Component
public class UserHandler {
    @Autowired
    private UserRepository userRepository;

    public Mono<User> save(User user) {
        System.out.println(user.getUsername());
        return userRepository.save(user);
//                .onErrorResume(e -> userRepository.findByUsername(user.getUsername())
//                        .flatMap(originalUser -> {
//                            user.setId(originalUser.getId());
//                            return userRepository.save(user);
//                        }));
    }

    public Mono<Long> deleteByUsername(String username) {
        return userRepository.deleteByUsername(username);
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

}
