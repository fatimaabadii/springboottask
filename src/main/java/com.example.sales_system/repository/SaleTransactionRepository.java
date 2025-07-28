package com.example.sales_system.repository;

import com.example.sales_system.model.SaleTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleTransactionRepository extends JpaRepository<SaleTransaction, Long> {
    // Full CRUD inherited
}
