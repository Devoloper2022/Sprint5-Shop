package org.example.intershop.config;


import org.example.intershop.client.ApiClient;
import org.example.intershop.client.api.PaymentApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayApiConfig {

    @Bean
    public PaymentApi paymentApi(@Value("${PAYMENT_CLIENT_HOST:localhost}") String restHost, @Value("${PAYMENT_CLIENT_PORT:9090}") int restPort) {
        return new PaymentApi(new ApiClient().setBasePath("http://" + restHost + ":" + restPort));
    }
}
