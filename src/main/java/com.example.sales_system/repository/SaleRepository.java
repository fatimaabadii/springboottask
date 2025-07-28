package com.example.sales_system.repository;

import com.example.sales_system.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    // Full CRUD inherited from JpaRepository
}
