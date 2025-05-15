package com.programacion.distribuida.purchaseorder.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime deliveredon;

    private LocalDateTime placedon;

    private Integer status;

    private Integer total;

    private Long customerId;
}