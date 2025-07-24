package com.programacion.distribuida.customers.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PurchaseOrderDto {
    private String id;
    private BigDecimal total;
    private String status;
    private LocalDateTime placedOn;
    private LocalDateTime deliveredOn;

    private CustomerDto customer;

    List<LineItemDto> lineItems;
}
