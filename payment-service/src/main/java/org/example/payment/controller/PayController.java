package org.example.payment.controller;

import lombok.RequiredArgsConstructor;

import org.example.payment.service.PaymentService;
import org.example.payment.service.api.PaymentApi;
import org.example.payment.service.model.BalanceResponse;
import org.example.payment.service.model.PaymentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;




@Controller
@RequiredArgsConstructor
public class PayController implements PaymentApi {
    private final PaymentService paymentService;


    public Mono<ResponseEntity<BalanceResponse>> getBalance(final ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(paymentService.getBalance()));
    }

    public Mono<ResponseEntity<Void>> makePayment(
            Mono<PaymentRequest> paymentRequest,
            final ServerWebExchange exchange
    ) {
        return paymentService.makePayment(paymentRequest)
                .flatMap(comparison -> {
                    if (comparison <= 0) {
                        return Mono.just(ResponseEntity.ok().build());
                    } else {
                        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).build());
                    }
                });
    }
}
