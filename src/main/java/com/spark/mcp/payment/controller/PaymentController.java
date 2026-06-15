package com.spark.mcp.payment.controller;

import com.spark.mcp.payment.dto.CreateOrderRequest;
import com.spark.mcp.payment.entity.PaymentTransaction;
import com.spark.mcp.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(
            @RequestBody CreateOrderRequest request) {

        return ResponseEntity.ok(
                paymentService.createOrder(request)
        );
    }

    @GetMapping(value = "/pay/{orderId}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String payPage(@PathVariable String orderId) {

        PaymentTransaction payment =
                paymentService.getOrder(orderId);

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <title>Cashfree Checkout</title>
            <script src="https://sdk.cashfree.com/js/v3/cashfree.js"></script>
        </head>
        <body>

        <h2>Testing Cashfree Payment</h2>

        <button onclick="payNow()">
            Pay ₹%s
        </button>

        <script>

        async function payNow() {

            const cashfree = Cashfree({
                mode: "sandbox"
            });

            await cashfree.checkout({
                paymentSessionId: "%s",
                redirectTarget: "_self"
            });
        }

        </script>

        </body>
        </html>
        """.formatted(
                payment.getAmount(),
                payment.getPaymentSessionId()
        );
    }
}
