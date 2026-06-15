package com.spark.mcp.payment.dto;

import lombok.Data;

@Data
public class CreateOrderRequest {

    private Double amount;
    private String customerId;
    private String customerEmail;
    private String customerPhone;
}