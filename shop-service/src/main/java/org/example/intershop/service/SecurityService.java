package org.example.intershop.service;

import lombok.RequiredArgsConstructor;
import org.example.intershop.DTO.UserDto;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;

    public Mono<Long> getCurrentUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .handle((authentication, sink) -> {
                    if (authentication == null || !authentication.isAuthenticated()) {
                        sink.error(new IllegalStateException("Пользователь не аутентифицирован"));
                    } else {
                        sink.next(authentication.getName());
                    }
                })
                .cast(String.class)
                .flatMap(userService::findByName)
                .map(UserDto::getId);
    }
}
