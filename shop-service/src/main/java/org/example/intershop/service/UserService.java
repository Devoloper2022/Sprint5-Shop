package org.example.intershop.service;

import org.example.intershop.DTO.UserDto;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserDto> findByName(String name);

    Mono<String> registerUser(UserDto userDto, ServerWebExchange exchange);
}
