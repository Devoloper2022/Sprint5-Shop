package org.example.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.example.payment.service.model.BalanceResponse;
import org.example.payment.service.model.PaymentRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BasicPaymentService implements PaymentService {
    @Value("${service.default-balance}")
    private BigDecimal defaultBalance;

    @Override
    public BalanceResponse getBalance() {
        return new BalanceResponse()
                .balance(defaultBalance);
    }

    @Override
    public Mono<Integer> makePayment(Mono<PaymentRequest> paymentRequest) {
        return paymentRequest
                .map(request -> request.getSum().compareTo(defaultBalance));
    }
}