package org.example.payment.service;

import org.example.payment.service.model.BalanceResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import org.example.payment.service.model.PaymentRequest;


public interface PaymentService {
    BalanceResponse getBalance();

    Mono<Integer> makePayment(Mono<PaymentRequest> paymentRequest);

}
