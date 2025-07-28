package com.example.sales_system.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class SaleTransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long transactionId;

    private Integer oldQuantity;
    private Double oldPrice;

    private Integer newQuantity;
    private Double newPrice;

    private LocalDateTime updateTime;
}
