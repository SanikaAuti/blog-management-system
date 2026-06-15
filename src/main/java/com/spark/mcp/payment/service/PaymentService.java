package com.spark.mcp.payment.service;

import com.spark.mcp.payment.dto.CreateOrderRequest;
import com.spark.mcp.payment.entity.PaymentTransaction;
import com.spark.mcp.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    @Value("${cashfree.client-id}")
    private String clientId;

    @Value("${cashfree.client-secret}")
    private String clientSecret;

    @Value("${cashfree.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> createOrder(
            CreateOrderRequest request) {

        String orderId =
                "ORD_" + UUID.randomUUID();

        Map<String, Object> customerDetails =
                new HashMap<>();

        customerDetails.put("customer_id",
                request.getCustomerId());

        customerDetails.put("customer_email",
                request.getCustomerEmail());

        customerDetails.put("customer_phone",
                request.getCustomerPhone());

        Map<String, Object> body =
                new HashMap<>();

        body.put("order_id", orderId);
        body.put("order_amount", request.getAmount());
        body.put("order_currency", "INR");
        body.put("customer_details", customerDetails);

        HttpHeaders headers = new HttpHeaders();

        headers.set("x-client-id", clientId);
        headers.set("x-client-secret", clientSecret);
        headers.set("x-api-version", "2023-08-01");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        baseUrl + "/pg/orders",
                        HttpMethod.POST,
                        entity,
                        Map.class);

        String paymentSessionId =
                (String) response.getBody()
                        .get("payment_session_id");

        repository.save(
                PaymentTransaction.builder()
                        .orderId(orderId)
                        .amount(request.getAmount())
                        .customerId(request.getCustomerId())
                        .customerEmail(request.getCustomerEmail())
                        .paymentSessionId(paymentSessionId)
                        .paymentStatus("PENDING")
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        return response.getBody();
    }

    public PaymentTransaction getOrder(String orderId) {
        return repository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
