package com.spark.mcp.payment.controller;

import com.spark.mcp.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentRepository repository;

    @PostMapping("/cashfree")
    public ResponseEntity<String> handleWebhook(
            @RequestBody Map<String, Object> payload) {

        Map<String, Object> data =
                (Map<String, Object>) payload.get("data");

        String orderId =
                (String) data.get("order_id");

        repository.findByOrderId(orderId)
                .ifPresent(payment -> {
                    payment.setPaymentStatus("SUCCESS");
                    repository.save(payment);
                });

        return ResponseEntity.ok("received");
    }
}
